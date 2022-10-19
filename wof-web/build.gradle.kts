import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform") version "1.7.10"
    id("org.jetbrains.compose") version "1.2.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

group = "com.tutuland.wof.web"
version = "1.1.1"

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("com.tutuland.wof.core:core-js:1.1.1")
                implementation(compose.runtime)
                implementation(compose.web.core)
                implementation(compose.web.svg)
            }
        }
    }
}
