plugins {
    idea
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    `java-library`
}

repositories {
    mavenCentral()
}

sourceSets {
    create("testIntegration") {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output

        kotlin {
            main {
                srcDirs("src/main/kotlin")   
            }
            test {
                srcDirs("src/test/kotlin")
            }
        }
    }
}

val testIntegrationImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
val testIntegrationRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get())
}

dependencies {
    implementation(libs.guava)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.serialization)
    
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testIntegrationImplementation(libs.junit.jupiter.engine)
    testIntegrationRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

idea {
    module {
        testSources.from(sourceSets["testIntegration"].kotlin.srcDirs)
        testResources.from(sourceSets["testIntegration"].resources.srcDirs)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
