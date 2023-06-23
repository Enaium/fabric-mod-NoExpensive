subprojects {
    dependencies.add("mappings", "net.fabricmc:yarn:${property("fabric.yarn.version")}:v2")
    dependencies.add("modImplementation", "net.fabricmc.fabric-api:fabric-api:${property("fabric.api.version")}")
}