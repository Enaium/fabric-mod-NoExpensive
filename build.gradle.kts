allprojects {
    group = "cn.enaium"
    version = rootProject.property("version").toString()

    repositories {
        mavenCentral {
            metadataSources {
                mavenPom()
                artifact()
                ignoreGradleMetadataRedirection()
            }
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        maven("https://jitpack.io")
    }
}