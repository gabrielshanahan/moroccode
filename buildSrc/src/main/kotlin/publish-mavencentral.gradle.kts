plugins {
    `maven-publish`
    signing
}

val PUBLISH_GROUP_ID by extra { "io.github.gabrielshanahan" }
val PUBLISH_ARTIFACT_ID: String by extra { rootProject.name }
val PUBLISH_VERSION by extra { "1.0.0-SNAPSHOT"}

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

publishing {
    publications {
        create<MavenPublication>("release") {
            // The coordinates of the library, being set from variables that
            // we'll set up in a moment
            groupId = PUBLISH_GROUP_ID
            artifactId = PUBLISH_ARTIFACT_ID
            version = PUBLISH_VERSION

                    // Two artifacts, the `aar` and the sources
            artifact(tasks["jar"])
            artifact(tasks["kotlinSourcesJar"])

            // Self-explanatory metadata for the most part
            pom {
                name.set(PUBLISH_ARTIFACT_ID)
                description.set("Quick, lightweight Kotlin library for equality checking & hash codes.")
                // If your project has a dedicated site, use its URL here
                url.set("https://github.com/gabriel-shanahan/moroccode")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("gabrielshanahan")
                        name.set("Gabriel Shanahan")
                        email.set("GabrielShanahan@gmail.com")
                    }
                }
                // Version control info, if you're using GitHub, follow the format as seen here
                scm {
                    connection.set("scm:git:github.com/gabrielshanahan/moroccode.git")
                    developerConnection.set("scm:git:ssh://github.com/gabrielshanahan/moroccode.git")
                    url.set("https://github.com/gabrielshanahan/moroccode/tree/master")
                }
                // A slightly hacky fix so that your POM will include any transitive dependencies
                // that your library builds upon
                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")

                    project.configurations.getByName("implementation").allDependencies.forEach {
                        val dependencyNode = dependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("groupId", it.group)
                        dependencyNode.appendNode("artifactId", it.name)
                        dependencyNode.appendNode("version", it.version)
                    }
                }
            }
        }
    }
    repositories {
        // The repository to publish to, Sonatype/MavenCentral
        maven {
            // This is an arbitrary name, you may also use "mavencentral" or
            // any other name that's descriptive for you
            name = "sonatype"

            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
            // You only need this if you want to publish snapshots, otherwise just set the URL
            // to the release repo directly
            url = uri(if(version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            // The username and password we've fetched earlier
            credentials {
                val ossrhUsername: String? by project
                val ossrhPassword: String? by project
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
