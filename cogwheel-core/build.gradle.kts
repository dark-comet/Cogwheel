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
                srcDirs("src/testIntegration/kotlin")
            }
            resources {
                srcDirs("src/testIntegration/resources")
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

kotlin.target.compilations.getByName("testIntegration").associateWith(kotlin.target.compilations.getByName("test"))

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

val testIntegration = task<Test>("testIntegration") {
    description = "Run integration tests"
    group = "verification"
    
    testClassesDirs = sourceSets["testIntegration"].output.classesDirs
    classpath = sourceSets["testIntegration"].runtimeClasspath
    
    shouldRunAfter("test")
    
    useJUnitPlatform()
    
    testLogging {
        events("passed")
    }
}

tasks.check {
    dependsOn(testIntegration)
}