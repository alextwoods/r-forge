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

extra["displayName"] = "Forge :: R :: Codegen :: Test"
extra["moduleName"] = "software.amazon.forge.r.codegen.test"

tasks["jar"].enabled = false

plugins {
    id("software.amazon.smithy").version("0.5.3")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":forge-r-codegen"))
}

tasks.register<Copy>("copyWhiteLabelGem") {
    from("$buildDir/smithyprojections/smithy-ruby-codegen-test/white-label/ruby-codegen")
    into("$buildDir/../../projections/")
}
tasks.register<Copy>("copyWeatherServiceGem") {
    from("$buildDir/smithyprojections/smithy-ruby-codegen-test/weather-service/ruby-codegen")
    into("$buildDir/../../projections/")
}
tasks.register<Copy>("copyIntegrationSpecs") {
    from("./integration-specs")
    into("$buildDir/smithyprojections/smithy-ruby-codegen-test/white-label/ruby-codegen/white_label/spec")
}
tasks.register<Copy>("copySteepfile") {
    from("./Steepfile")
    into("$buildDir/smithyprojections/smithy-ruby-codegen-test/white-label/ruby-codegen/white_label")
}
tasks["build"].finalizedBy(
    tasks["copyWeatherServiceGem"],
    tasks["copyWhiteLabelGem"],
    tasks["copyIntegrationSpecs"],
    tasks["copySteepfile"]
)