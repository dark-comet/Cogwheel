plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Cogwheel"

// Main library components
include(":cogwheel-core")
include(":cogwheel-dsl")

// Test consumer clients
include(":test-client-kotlin")
include(":test-client-java")