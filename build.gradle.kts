val JAVADOC_DIR: String by extra

plugins {
    kotlin("jvm") version "1.3.61"

    `java-library`
    jacoco

    id("org.jmailen.kotlinter") version "2.1.3"
    id("io.gitlab.arturbosch.detekt").version("1.2.2")
    id("org.sonarqube") version "2.8"
    id("org.jetbrains.dokka") version "0.10.0"

    id("publish-mavencentral")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")

    // This is needed due to a known issue with KotlinTest: https://github.com/kotlintest/kotlintest/issues/639
    testImplementation("org.slf4j", "slf4j-simple", "1.7.26")

    // Adds detekt wrapper around ktlint
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.0.1")
}

tasks {
    test {
        useJUnitPlatform()
        // Always run detekt (and through the klint wrapper, klint) and jacoco when running tests
        finalizedBy(detekt)
        finalizedBy(jacocoTestReport)

        testLogging {
            // Make sure output from
            // standard out or error is shown
            // in Gradle output.
            showStandardStreams = true
        }
    }

    dokka {
        outputFormat = "html"
        outputDirectory = JAVADOC_DIR
        configuration {
            includeNonPublic = true
            reportUndocumented = true
        }
    }
}

detekt {
    toolVersion = "1.2.2"
    parallel = true // Builds the AST in parallel. Rules are always executed in parallel.
}

