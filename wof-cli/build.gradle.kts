plugins {
    kotlin("multiplatform") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

group = "com.tutuland.wof.cli"
version = "1.1.1"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.tutuland.wof.core:core-desktop:1.1.1")
                implementation("com.github.kotlin-inquirer:kotlin-inquirer:0.1.0")
            }
        }
    }
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}
