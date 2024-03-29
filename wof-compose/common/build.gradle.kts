import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = "com.tutuland.wof.compose"
version = "1.2.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
        val commonMain by getting {
            dependencies {
                api(libs.moko.resources.common)
                api(libs.wof.core.common)
                api(compose.ui)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.preview)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.test)
                implementation(libs.moko.resources.test)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.accompanist.navigation.animation)
                implementation(libs.coil)
                implementation(libs.moko.resources.compose)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.moko.resources.compose)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.tutuland.wof.compose"
}
