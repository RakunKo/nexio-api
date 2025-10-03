plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "nexio"
version = "0.0.1"
description = "nexio-api"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val generateVersionFile by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/version")
    outputs.dir(outputDir)
    doLast {
        val file = outputDir.get().file("ApiVersion.kt").asFile
        file.parentFile.mkdirs()
        file.writeText("""
            package com.nexio

            object ApiVersion {
                const val API_VERSION = "${project.version}"
            }
        """.trimIndent())
    }
}

kotlin.sourceSets.main {
    kotlin.srcDir(layout.buildDirectory.dir("generated/version"))
}

tasks.compileKotlin {
    dependsOn(generateVersionFile)
}
