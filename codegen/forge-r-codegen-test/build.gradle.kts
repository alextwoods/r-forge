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

tasks.register<Copy>("copyCrc") {
    from("$buildDir/smithyprojections/forge-r-codegen-test/Crc/r-forge-codegen/crc.cpp")
    into("/Users/alexwoo/R/AwsSdkCrt/src/")
}

tasks.register<Copy>("copyCommon") {
    from("$buildDir/smithyprojections/forge-r-codegen-test/Common/r-forge-codegen/common.cpp")
    into("/Users/alexwoo/R/AwsSdkCrt/src/")
}

tasks["build"].finalizedBy(
    tasks["copyCrc"],
    tasks["copyCommon"]
)