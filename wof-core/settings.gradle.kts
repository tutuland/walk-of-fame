dependencyResolutionManagement {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "WoFCore"

include(":core")

enableFeaturePreview("VERSION_CATALOGS")
