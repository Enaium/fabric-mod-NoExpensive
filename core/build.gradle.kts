plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.fabric.kotlin)
    implementation(libs.gson)
}

kotlin {
    jvmToolchain(8)
}