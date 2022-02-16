import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.codingfeline.buildkonfig")
    id("com.android.library")
    id("maven-publish")
}

group = "com.tutuland.wof.core"
version = "1.1.0"

kotlin {
    android {
        publishLibraryVariants("release")
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser()
    }
    ios()
    // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
    iosSimulatorArm64()

    cocoapods {
        summary = "Common library with business logic for walk-of-fam clients"
        homepage = "https://github.com/tutuland/walk-of-fame"
        ios.deploymentTarget = "12.4"
        podfile = project.file("../driver-iOS/Podfile")
        framework {
            isStatic = false
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
                implementation(libs.coroutines.core)
                implementation(libs.dateTime)
                implementation(libs.koin.core)
                implementation(libs.bundles.ktor.common)
                api(libs.touchlab.kermit)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.shared.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okHttp)
            }
        }
        val androidTest by getting
        val desktopMain by getting {
            dependencies {
                api(libs.slf4j.simple)
                implementation(libs.ktor.client.java)
            }
        }
        val desktopTest by getting
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
        val jsTest by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
                val coroutineCore = libs.coroutines.core.get()
                implementation(
                    "${coroutineCore.module.group}:${coroutineCore.module.name}:${coroutineCore.versionConstraint.displayName}"
                ) {
                    version {
                        strictly(libs.versions.coroutines.get())
                    }
                }
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

android {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(libs.versions.minSdk.get().toInt())
        targetSdkVersion(libs.versions.targetSdk.get().toInt())
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

buildkonfig {
    packageName = "com.tutuland.wof.core"
    defaultConfigs {
        val props = Properties()
        props.load(project.rootProject.file("local.properties").inputStream())
        val apiKey = props["com.tutuland.wof.core.apikey"]?.toString() ?: "couldNotLoadApiKey"

        buildConfigField(STRING, "API_KEY", apiKey)
        buildConfigField(STRING, "BASE_URL", "https://api.themoviedb.org/3/")
    }
}
