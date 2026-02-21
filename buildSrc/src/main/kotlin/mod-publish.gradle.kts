import me.modmuss50.mpp.PublishModTask

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

afterEvaluate {
    publishMods {
        val disableObfuscation = properties.getOrDefault("fabric.loom.disableObfuscation", false).toString().toBoolean()
        file = tasks.named<AbstractArchiveTask>(if (disableObfuscation) "jar" else "remapJar").get().archiveFile.get()
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
            dependsOn(tasks.named(if (disableObfuscation) "jar" else "remapJar"))
        }
    }
}