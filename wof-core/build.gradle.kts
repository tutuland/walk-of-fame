buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs
        classpath(libs.bundles.gradlePlugins)
        classpath(kotlin("gradle-plugin", libs.versions.kotlin.get()))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// TODO: this seemed to be causing side-effects for the "js { browser() }" build configuration,
//  commenting for now
//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}
