/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.forge.r.codegen;

import software.amazon.smithy.build.PluginContext;
import software.amazon.smithy.codegen.core.CodegenException;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.model.transform.ModelTransformer;
import software.amazon.smithy.utils.SmithyInternalApi;

import java.util.logging.Logger;

@SmithyInternalApi
public class CodegenOrchestrator {

    private static final Logger LOGGER =
            Logger.getLogger(CodegenOrchestrator.class.getName());

    private final GenerationContext context;

    public CodegenOrchestrator(PluginContext pluginContext) {

        RForgeSettings RForgeSettings =
                software.amazon.forge.r.codegen.RForgeSettings.from(pluginContext.getSettings());


        Model resolvedModel = pluginContext.getModel();

        System.out.println("\n\n-------------------------------\nRunning Forge Code Generation\n");

        ServiceShape service =
                resolvedModel.expectShape(RForgeSettings.getService())
                        .asServiceShape().orElseThrow(
                        () -> new CodegenException("Shape is not a service"));


        // Add unique operation input/output shapes
        resolvedModel = ModelTransformer.create()
                .createDedicatedInputAndOutput(resolvedModel, "Input", "Output");

        context = new GenerationContext(
                RForgeSettings,
                pluginContext.getFileManifest(),
                resolvedModel,
                service);
    }

    public void execute() {
        System.out.println("Building bindings for: " + context.getService().toShapeId());
        (new BindingGenerator(context)).render();
    }
}
