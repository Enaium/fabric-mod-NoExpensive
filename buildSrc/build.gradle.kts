plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "legacy-fabric"
        url = uri("https://repo.legacyfabric.net/repository/legacyfabric/")
    }
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.loom)
    implementation(libs.legacy.looming)
    implementation(libs.publish)
    implementation(libs.fmg)
}