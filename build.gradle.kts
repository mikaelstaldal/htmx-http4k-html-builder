group = "nu.staldal"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "2.1.0"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1" // For creating JAR with dependencies
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

val mainClassName = "nu.staldal.htmxhttp4kbuilder.MainKt"

application {
    mainClass.set(mainClassName)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // Kotlin standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    
    // HTTP4k (Core and HTMX modules)
    implementation(platform("org.http4k:http4k-bom:5.44.2.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-htmx")
    
    // Bootstrap (from npm)
    implementation("org.webjars.npm:bootstrap:5.3.3")
    
    // SLF4J runtime implementation
    runtimeOnly("org.slf4j:slf4j-simple:2.0.13")

    // Custom dependency
    implementation("nu.staldal:kotlin-html-builder:0.4.0")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    manifest {
        attributes(mapOf("Main-Class" to mainClassName)) // Set the `Main-Class` in manifest
    }
    archiveClassifier.set("jar-with-dependencies")
}
