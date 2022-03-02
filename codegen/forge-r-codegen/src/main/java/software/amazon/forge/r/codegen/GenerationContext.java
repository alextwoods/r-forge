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

import software.amazon.smithy.build.FileManifest;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.shapes.ServiceShape;
import software.amazon.smithy.utils.SmithyUnstableApi;

/**
 * The context for code generation.
 * Includes all objects required for generators
 * to render code including the transformed model,
 * loaded integrations, service, ect.
 */
@SmithyUnstableApi
public class GenerationContext {

    private final RForgeSettings RForgeSettings;
    private final FileManifest fileManifest;
    private final Model model;
    private final ServiceShape service;

    public GenerationContext(RForgeSettings RForgeSettings,
                             FileManifest fileManifest,
                             Model model,
                             ServiceShape service) {

        this.RForgeSettings = RForgeSettings;
        this.fileManifest = fileManifest;
        this.model = model;
        this.service = service;
    }

    public RForgeSettings getRubySettings() {
        return RForgeSettings;
    }

    public FileManifest getFileManifest() {
        return fileManifest;
    }

    public Model getModel() {
        return model;
    }

    public ServiceShape getService() {
        return service;
    }

}
