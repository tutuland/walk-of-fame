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
                useModule("org.jetbrains.compose:compose-gradle-plugin:1.1.0-alpha02")
            }
        }
    }
}

rootProject.name = "WoFCompose"

include(":android")
include(":common")
include(":desktop")

enableFeaturePreview("VERSION_CATALOGS")
