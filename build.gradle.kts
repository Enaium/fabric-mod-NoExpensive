import com.github.breadmoirai.githubreleaseplugin.GithubReleaseExtension
import com.modrinth.minotaur.ModrinthExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
}

buildscript {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven {
            name = "legacy-fabric"
            url = uri("https://repo.legacyfabric.net/repository/legacyfabric/")
        }
        gradlePluginPortal()
        mavenCentral()
    }

    val loomVersion: String by project
    val minotaurVersion: String by project
    val githubReleaseVersion: String by project
    val kotlinVersion: String by project

    dependencies {
        classpath("net.fabricmc:fabric-loom:${loomVersion}")
        classpath("net.legacyfabric:legacy-looming:${loomVersion}")
        classpath("com.modrinth.minotaur:Minotaur:${minotaurVersion}")
        classpath("com.github.breadmoirai:github-release:${githubReleaseVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
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
    version = "1.10.0"
}

subprojects {
    if (!name.startsWith("_")) {
        return@subprojects
    }

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
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
        inputs.property("currentTimeMillis", System.currentTimeMillis())

        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to project.version.toString(),
                    "minecraft_version" to properties["minecraft.version"].toString()
                        .let { "${it.subSequence(0, it.lastIndexOf("."))}.x" },
                    "java_version" to properties["java.version"].toString(),
                    "api_name" to if (parent?.name == "legacy") "legacy-fabric-api-base" else "fabric-api-base"
                )
            )
        }

        filesMatching("noexpensive.mixins.json") {
            expand(
                mapOf(
                    "java_version" to properties["java.version"],
                    "mixin_list" to file("src/main/java/cn/enaium/noexpensive/mixin").listFiles()
                        ?.joinToString(", ", "[", "]") { it.name.subSequence(0, it.name.lastIndexOf(".")).toString() }
                )
            )
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

    //Legacy and Modern compatibility dependencies
    dependencies.add("minecraft", "com.mojang:minecraft:${property("minecraft.version")}")
    dependencies.add("modImplementation", "net.fabricmc:fabric-loader:${property("fabric.loader.version")}")
    dependencies.add(
        "modImplementation",
        "net.fabricmc:fabric-language-kotlin:${property("fabricKotlinVersion")}+kotlin.${property("kotlinVersion")}"
    ) {
        exclude(module = "*")
    }

    property("java.version").toString().toInt().let {
        tasks.withType<JavaCompile> {
            options.release.set(it)
        }

        java.sourceCompatibility = JavaVersion.toVersion(it)
        java.targetCompatibility = JavaVersion.toVersion(it)
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(properties["java.version"].toString().let { if (it == "8") JvmTarget.JVM_1_8 else JvmTarget.fromTarget(it) })
        }
    }

    afterEvaluate {
        configurations.runtimeClasspath.get().forEach {
            if (it.name.startsWith("sponge-mixin")) {
                tasks.named<JavaExec>("runClient") {
                    jvmArgs("-javaagent:${it.absolutePath}")
                }
                tasks.named<JavaExec>("runServer") {
                    jvmArgs("-javaagent:${it.absolutePath}")
                }
            }
        }

        properties["modrinth.token"]?.let {
            configure<ModrinthExtension> {
                projectId.set("2nz0kJ1N")
                versionNumber.set(version.toString())
                versionName.set("$archivesBaseName-$version")
                gameVersions.set(listOf(property("minecraft.version").toString()))
                versionType.set("release")
                loaders.set(listOf("fabric"))
                dependencies {
                    required.project(if (parent?.name == "legacy") "legacy-fabric-api" else "fabric-api")
                    required.project("fabric-language-kotlin")
                }
                uploadFile.set(tasks.named("remapJar"))
                changelog.set(rootProject.file("changelog.md").readText(Charsets.UTF_8))
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
                body.set(rootProject.file("changelog.md").readText(Charsets.UTF_8))
                releaseAssets(listOf(tasks.named("remapJar")))
            }
        }
    }
}