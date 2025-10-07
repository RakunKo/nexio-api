plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
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
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //r2dbc
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation ("io.asyncer:r2dbc-mysql:1.0.4")
    //flyway
    implementation("org.flywaydb:flyway-core:10.5.0")
    implementation("org.flywaydb:flyway-mysql:10.8.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    //docs
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.7.0")
    //jooq
    implementation("org.jooq:jooq:3.19.0")
    implementation("org.jooq:jooq-kotlin:3.19.0")
    implementation("org.jooq:jooq-kotlin-coroutines:3.19.0")
    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

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
