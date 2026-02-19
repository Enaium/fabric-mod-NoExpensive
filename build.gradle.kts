allprojects {
    group = "cn.enaium"
    version = rootProject.property("version").toString()

    repositories {
        mavenCentral()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}