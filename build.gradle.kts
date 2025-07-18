import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.PublishModTask
import org.gradle.kotlin.dsl.withType
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
    val kotlinVersion: String by project
    val modPublishVersion: String by project

    dependencies {
        classpath("net.fabricmc:fabric-loom:${loomVersion}")
        classpath("net.legacyfabric:legacy-looming:${loomVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("me.modmuss50.mod-publish-plugin:me.modmuss50.mod-publish-plugin.gradle.plugin:${modPublishVersion}")
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
    version = rootProject.property("version").toString()
}

subprojects {
    if (!name.startsWith("_")) {
        return@subprojects
    }

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("fabric-loom")
        plugin("me.modmuss50.mod-publish-plugin")
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
            jvmTarget.set(
                properties["java.version"].toString()
                    .let { if (it == "8") JvmTarget.JVM_1_8 else JvmTarget.fromTarget(it) })
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

        configure<ModPublishExtension> {
            file = tasks.named<AbstractArchiveTask>("remapJar").get().archiveFile.get()
            type = STABLE
            displayName = "NoExpensive ${project.version}"
            changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
            modLoaders.add("fabric")

            curseforge {
                projectId = "387108"
                accessToken = providers.gradleProperty("curseforge.token")
                minecraftVersions.add(property("minecraft.version").toString())
                requires("fabric-language-kotlin", if (parent?.name == "legacy") "legacy-fabric-api" else "fabric-api")
            }

            modrinth {
                projectId = "2nz0kJ1N"
                accessToken = providers.gradleProperty("modrinth.token")
                minecraftVersions.add(property("minecraft.version").toString())
                requires("fabric-language-kotlin", if (parent?.name == "legacy") "legacy-fabric-api" else "fabric-api")
            }

            github {
                repository = "Enaium/fabric-mod-NoExpensive"
                accessToken = providers.gradleProperty("github.token")
                commitish = "master"
            }

            tasks.withType<PublishModTask>().configureEach {
                dependsOn(tasks.named("remapJar"))
            }
        }
    }
}