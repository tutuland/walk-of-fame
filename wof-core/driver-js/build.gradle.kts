plugins {
    kotlin("js")
}

kotlin {
    js {
        browser()
        binaries.executable()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.coroutines.core)
}
