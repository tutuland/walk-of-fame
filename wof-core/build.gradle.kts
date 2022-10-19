buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.bundles.gradlePlugins)
        classpath(kotlin("gradle-plugin", libs.versions.kotlin.get()))
    }
}
