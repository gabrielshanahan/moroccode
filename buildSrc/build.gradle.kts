plugins {
    // Necessary to make publish-mavencentral.gradle.kts work, see https://discuss.gradle.org/t/plugins-and-apply-from-in-the-kotlin-dsl/28662/8
    // Throws a "Unsupported Kotlin plugin version" if using Kotlin > 1.3.50, you should be able to safely ignore it
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    jcenter()
}