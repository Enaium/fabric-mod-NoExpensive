plugins {
    id("cn.enaium.fabric-multi-game")
}

fmg {
    common.set(project(":core"))
}

subprojects {
    apply(plugin = "mod-publish")
}