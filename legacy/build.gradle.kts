subprojects {
    apply {
        plugin("legacy-looming")
    }

    dependencies {
        dependencies.add("mappings", "net.legacyfabric:yarn:${property("fabric.yarn.version")}:v2")
        dependencies.add("modImplementation", "net.legacyfabric.legacy-fabric-api:legacy-fabric-api:${property("fabric.api.version")}")
    }
}