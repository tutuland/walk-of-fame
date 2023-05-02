import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.codingfeline.buildkonfig")
    id("com.android.library")
    id("maven-publish")
    id("com.squareup.sqldelight")
}

group = "com.tutuland.wof.core"
version = "1.2.0"

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
        ios.deploymentTarget = "14.1"
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
                api(libs.coroutines.core)
                api(libs.koin.core)
                api(libs.touchlab.kermit)
                implementation(libs.dateTime)
                implementation(libs.sqlDelight.coroutinesExt)
                implementation(libs.bundles.commonKtor)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okHttp)
                implementation(libs.sqlDelight.android)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(libs.slf4j.simple)
                implementation(libs.ktor.client.java)
                implementation(libs.sqlDelight.jvm)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                //JS will not use a real DB for now
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.ios)
                implementation(libs.sqlDelight.native)
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
    }
}

android {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(libs.versions.minSdk.get().toInt())
        targetSdkVersion(libs.versions.targetSdk.get().toInt())
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
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

sqldelight {
    database("WofDb") {
        packageName = "com.tutuland.wof.core.db"
    }
    linkSqlite = true
}
