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

description = "Generates Ruby code from Smithy models"
extra["displayName"] = "Forge :: R :: Codegen"
extra["moduleName"] = "software.amazon.forge.r.codegen"

dependencies {
    api("software.amazon.smithy:smithy-codegen-core:[1.17.0,2.0.0[")
    implementation("software.amazon.smithy:smithy-waiters:[1.4.0,2.0.0[")
    implementation("software.amazon.smithy:smithy-protocol-test-traits:[1.9.0,2.0.0[")
}
