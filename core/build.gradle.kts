plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(libs.mineconf)
    implementation(libs.fabric.kotlin)
}

kotlin {
    jvmToolchain(8)
}