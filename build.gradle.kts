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

    dependencies {
        classpath("net.fabricmc:fabric-loom:${loomVersion}")
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
    version = "1.5.0"
}

subprojects {
    apply {
        plugin("java")
        plugin("fabric-loom")
    }

    val archivesBaseName: String by project

    base {
        archivesName.set(archivesBaseName)
    }

    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version.toString()))
        }
    }

    repositories {
        mavenCentral()
    }
}