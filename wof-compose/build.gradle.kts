buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.bundles.gradlePlugins)
        classpath(kotlin("gradle-plugin", libs.versions.kotlin.get()))
    }
}
