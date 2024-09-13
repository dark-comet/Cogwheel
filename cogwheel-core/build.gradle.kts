import java.nio.file.Files
import java.nio.file.Path

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
    implementation(libs.ktor.client.websockets)
    implementation(libs.kotlinx.serialization)
    
    api(libs.slf4j.api)
    implementation(libs.slf4j.provider)
    
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

    val localBotTokensFile = project.file("testSecrets.txt").toPath()
    if (Files.exists(localBotTokensFile)) {
        loadLocalBotTokensIfApplicable(this, localBotTokensFile)
    }

    shouldRunAfter("test")
    
    useJUnitPlatform()
    
    testLogging {
        events("passed")
    }
}

fun loadLocalBotTokensIfApplicable(test: Test, localBotTokensFile: Path) {
    /* Keys defined in testIntegration: TestDiscordClient class */
    val botTokenKey = "CW_CORE_CI_BOT_TOKEN"
    val oauth2TokenKey = "CW_CORE_CI_OAUTH2_TOKEN"
    
    val localTokens = Files.readAllLines(localBotTokensFile)
    val localBotToken = localTokens[0].replace("$botTokenKey=", "").trim()
    val localOAuth2Token = localTokens[1].replace("$oauth2TokenKey=", "").trim()

    if (localBotToken.isNotBlank()) {
        test.environment(botTokenKey, localBotToken)
    }

    if (localOAuth2Token.isNotBlank()) {
        test.environment(oauth2TokenKey, localOAuth2Token)
    }
}

tasks.check {
    dependsOn(testIntegration)
}