package software.amazon.forge.r.codegen.traits;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AbstractTrait;
import software.amazon.smithy.model.traits.AbstractTraitBuilder;
import software.amazon.smithy.model.traits.TraitService;
import software.amazon.smithy.utils.MapUtils;
import software.amazon.smithy.utils.ToSmithyBuilder;

import java.util.Optional;

public final class CrtBindingTrait extends AbstractTrait implements ToSmithyBuilder<CrtBindingTrait> {
    public static final ShapeId ID = ShapeId.from("forge.crt#crtBinding");

    private final String definedIn;
    private final String functionName;

    private CrtBindingTrait(Builder builder) {
        super(ID, builder.getSourceLocation());
        this.definedIn = builder.definedIn;
        this.functionName = builder.functionName;
    }

    /**
     * Gets the definedIn value.
     *
     * @return returns the optional definedIn value.
     */
    public Optional<String> getDefinedIn() {
        return Optional.ofNullable(definedIn);
    }

    public Optional<String> getFunctionName() {
        return Optional.ofNullable(functionName);
    }

    @Override
    protected Node createNode() {
        return new ObjectNode(MapUtils.of(), getSourceLocation())
                .withOptionalMember("definedIn", getDefinedIn().map(Node::from))
                .withOptionalMember("functionName", getDefinedIn().map(Node::from));
    }

    @Override
    public Builder toBuilder() {
        return builder().definedIn(definedIn).functionName(functionName).sourceLocation(getSourceLocation());
    }

    /**
     * @return Returns a builder used to create a RailJson trait.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder used to create a CrtBinding.
     */
    public static final class Builder extends AbstractTraitBuilder<CrtBindingTrait, Builder> {
        private String definedIn;
        private String functionName;

        public Builder definedIn(String definedIn) {
            this.definedIn = definedIn;
            return this;
        }

        public Builder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        @Override
        public CrtBindingTrait build() {
            return new CrtBindingTrait(this);
        }
    }

    public static final class Provider implements TraitService {
        @Override
        public ShapeId getShapeId() {
            return ID;
        }

        @Override
        public CrtBindingTrait createTrait(ShapeId target, Node value) {
            ObjectNode objectNode = value.expectObjectNode();
            String definedInValue = objectNode.getMember("definedIn")
                    .map(v -> v.expectStringNode().getValue()).orElse(null);
            String functionNameValue = objectNode.getMember("functionName")
                    .map(v -> v.expectStringNode().getValue()).orElse(null);
            return builder().sourceLocation(value)
                    .definedIn(definedInValue)
                    .functionName(functionNameValue).build();
        }
    }
}