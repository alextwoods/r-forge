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

import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.utils.SmithyUnstableApi;

import java.util.logging.Logger;

/**
 * Settings used by {@link RForgeCodegenPlugin}.
 */
@SmithyUnstableApi
public final class RForgeSettings {
    private static final Logger LOGGER = Logger.getLogger(RForgeSettings.class.getName());

    private static final String SERVICE = "service";
    private static final String MODULE = "module";

    private ShapeId service;
    private String module;

    /**
     * Create a settings object from a configuration object node.
     *
     * @param config Config object to load.
     * @return Returns the extracted settings.
     */
    public static RForgeSettings from(ObjectNode config) {
        RForgeSettings settings = new RForgeSettings();

        settings.setService(config.expectStringMember(SERVICE).expectShapeId());
        // module and namespace
        settings.setModule(config.expectStringMember(MODULE).getValue());

        LOGGER.info("Created R forge Settings: " + settings);

        return settings;
    }

    public ShapeId getService() {
        return service;
    }

    public void setService(ShapeId service) {
        this.service = service;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}


