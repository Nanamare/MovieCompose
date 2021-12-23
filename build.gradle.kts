// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {
        classpath(Deps.gradle)
        classpath(Deps.kotlin_plugin)
        classpath(Deps.hilt_plugin)
        classpath(kotlin("gradle-plugin", version = Versions.kotlin_version))
        classpath(kotlin("serialization", version = Versions.kotlin_version))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}