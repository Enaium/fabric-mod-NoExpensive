import com.github.breadmoirai.githubreleaseplugin.GithubReleaseExtension
import com.modrinth.minotaur.ModrinthExtension

plugins {
    id("java")
}

buildscript {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
        mavenCentral()
    }

    val loomVersion: String by project
    val minotaurVersion: String by project
    val githubReleaseVersion: String by project

    dependencies {
        classpath("net.fabricmc:fabric-loom:${loomVersion}")
        classpath("com.modrinth.minotaur:Minotaur:${minotaurVersion}")
        classpath("com.github.breadmoirai:github-release:${githubReleaseVersion}")
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(emptySet<File>())
        }
    }
}

allprojects {
    group = "cn.enaium"
    version = "1.6.3"
}

subprojects {
    apply {
        plugin("java")
        plugin("fabric-loom")
        plugin("com.modrinth.minotaur")
        plugin("com.github.breadmoirai.github-release")
    }

    val archivesBaseName: String by project

    base {
        archivesName.set(archivesBaseName)
    }

    version = "${property("minecraft.version")}-${version}"

    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version.toString()))
        }
    }

    repositories {
        mavenCentral()
    }

    sourceSets.main {
        resources {
            srcDir(file(rootProject.projectDir).resolve("resources"))
        }
    }

    dependencies.add("minecraft", "com.mojang:minecraft:${property("minecraft.version")}")
    dependencies.add("mappings", "net.fabricmc:yarn:${property("fabric.yarn.version")}:v2")
    dependencies.add("modImplementation", "net.fabricmc:fabric-loader:${property("fabric.loader.version")}")
    dependencies.add("modImplementation", "net.fabricmc.fabric-api:fabric-api:${property("fabric.api.version")}")

    property("java.version").toString().toInt().let {
        tasks.withType<JavaCompile> {
            options.release.set(it)
        }

        java.sourceCompatibility = JavaVersion.toVersion(it)
        java.targetCompatibility = JavaVersion.toVersion(it)
    }

    afterEvaluate {
        properties["modrinth.token"]?.let {
            configure<ModrinthExtension> {
                projectId.set("2nz0kJ1N")
                versionNumber.set(version.toString())
                versionName.set("$archivesBaseName-$version")
                gameVersions.set(listOf(property("minecraft.version").toString()))
                versionType.set("release")
                loaders.set(listOf("fabric"))
                dependencies {
                    required.project("fabric-api")
                }
                uploadFile.set(tasks.named("remapJar"))
                token.set(it.toString())
            }
        }

        properties["github.token"]?.let {
            configure<GithubReleaseExtension> {
                token(it.toString())
                owner.set("Enaium-FabricMC")
                repo.set("NoExpensive")
                tagName.set(version.toString())
                releaseName.set("$archivesBaseName-$version")
                targetCommitish.set("master")
                generateReleaseNotes.set(false)
                body.set("$archivesBaseName-$version")
                releaseAssets(listOf(tasks.named("remapJar")))
            }
        }
    }
}