pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jetbrains.compose") {
                useModule("org.jetbrains.compose:compose-gradle-plugin:1.0.1")
            }
        }
    }
}

rootProject.name = "WoF Core"

include(":core")
include(":driver-android")
include(":driver-desktop")

enableFeaturePreview("VERSION_CATALOGS")
