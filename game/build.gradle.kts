plugins {
    id("cn.enaium.fabric-multi-game")
}

fmg {
    common.set(project(":core"))
}

val mineconf = libs.versions.mineconf.get()

subprojects {
    apply(plugin = "mod-publish")

    val minecraftVersion = properties["minecraft.version"]

    val disableObfuscation = properties.getOrDefault("fabric.loom.disableObfuscation", false).toString().toBoolean()

    dependencies.add(
        if (disableObfuscation) "implementation" else "modImplementation",
        "cn.enaium:mineconf:${minecraftVersion}-${mineconf}"
    )
}