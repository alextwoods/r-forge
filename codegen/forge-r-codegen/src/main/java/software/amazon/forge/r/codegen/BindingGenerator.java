package software.amazon.forge.r.codegen;

import software.amazon.forge.r.codegen.traits.CTypeTrait;
import software.amazon.forge.r.codegen.traits.CrtBindingTrait;
import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.codegen.core.CodegenException;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.knowledge.TopDownIndex;
import software.amazon.smithy.model.shapes.MemberShape;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.Shape;
import software.amazon.smithy.model.shapes.StructureShape;
import software.amazon.smithy.utils.CaseUtils;
import software.amazon.smithy.utils.CodeWriter;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BindingGenerator {
    private final GenerationContext context;
    private final CodeWriter writer;
    private final Model model;

    public BindingGenerator(GenerationContext context) {
        this.context = context;
        this.model = context.getModel();
        this.writer = new CodeWriter();
    }

    public void render() {

        renderIncludes();
        renderFunctions();

        String fileName = writer.format("$L.cpp", context.getRubySettings().getModule());
        FileManifest fileManifest = context.getFileManifest();
        fileManifest.writeFile(fileName, writer.toString());
        System.out.println("Wrote bindings to: " + fileName);
    }

    private void renderFunctions() {
        // Generate each operation for the service. We do this here instead of via the operation visitor method to
        // limit it to the operations bound to the service.
        TopDownIndex topDownIndex = TopDownIndex.of(model);
        Set<OperationShape> containedOperations = new TreeSet<>(
                topDownIndex.getContainedOperations(context.getService()));
        containedOperations.stream()
                .sorted(Comparator.comparing((o) -> o.getId().getName()))
                .forEach(o -> renderFunction(o));
    }

    private void renderFunction(OperationShape o) {
        String rcppFunName = CaseUtils.toSnakeCase(o.getId().getName());
        String crtFunName = rcppFunName;
        if (o.hasTrait(CrtBindingTrait.class) && o.getTrait(CrtBindingTrait.class).get().getFunctionName().isPresent()) {
            crtFunName = o.getTrait(CrtBindingTrait.class).get().getFunctionName().get();
        }
        StructureShape input = model.expectShape(o.getInputShape(), StructureShape.class);
        StructureShape output = model.expectShape(o.getOutputShape(), StructureShape.class);

        if (output.getMember("ret").isPresent()) {
            MemberShape ret = output.getMember("ret").get();
            Shape retTarget = model.expectShape(ret.getTarget());
            String returnType = determineReturnType(retTarget);

            String rcppInputs = input.members().stream().map( (m) ->
                writer.format("$L $L", rcppType(m), m.getMemberName())
            ).collect(Collectors.joining(", "));

            writer.write("\n// [[Rcpp::export]]");
            writer.openBlock("$L $L($L) { ", returnType, rcppFunName, rcppInputs);

            String transformedArgs = generateConversions(input);

            if (retTarget.hasTrait(CTypeTrait.class)) {
                writer.write("return $L($L);", crtFunName, transformedArgs);
            } else if (retTarget.isStringShape()) {
                writer.write("return String( $L($L) );", crtFunName, transformedArgs);
            } else {
                throw new CodegenException ("Unable to generate return statement");
            }
            writer.closeBlock("}");
        }
        // TODO, handle different return styles

    }

    private String determineReturnType(Shape retTarget) {
        if (retTarget.hasTrait(CTypeTrait.class)) {
            return retTarget.getTrait(CTypeTrait.class).get().getValue();
        } else if (retTarget.isStringShape()) {
            return "String";
        } else {
            throw new CodegenException ("Unable to determine return type");
        }
    }

    private String generateConversions(StructureShape input) {
        return input.members().stream().map( (m) -> {
            Shape target = model.expectShape(m.getTarget());
            // TODO: We could use a visitor if there are many types to handle here
            if (target.isStringShape()) {
                String tempName = "temp_" + m.getMemberName();
                writer.write("std::string $L = Rcpp::as<std::string>($L[0]);", tempName, m.getMemberName());
                return writer.format("(const uint8_t*)$1L.c_str(), $1L.size()", tempName);
            } else {
                return m.getMemberName();
            }
        }).collect(Collectors.joining(", "));
    }

    private String rcppType(MemberShape m) {
        Shape target = model.expectShape(m.getTarget());
        if (target.isStringShape()) {
            return "StringVector";
        } else {
            return target.getTrait(CTypeTrait.class).get().getValue();
        }
    }

    private void renderIncludes() {
        TopDownIndex topDownIndex = TopDownIndex.of(model);
        Set<OperationShape> containedOperations = new TreeSet<>(
                topDownIndex.getContainedOperations(context.getService()));
        Set<String> includes = containedOperations.stream()
                .filter( (o) -> o.hasTrait(CrtBindingTrait.class) && o.getTrait(CrtBindingTrait.class).get().getDefinedIn().isPresent())
                .map( (o) -> o.getTrait(CrtBindingTrait.class).get().getDefinedIn().get())
                .collect(Collectors.toSet());


        writer.write("#include <Rcpp.h>");
        includes.forEach( (include) -> writer.write("#include <$L>", include));
        writer.write("using namespace Rcpp;");
    }
}
