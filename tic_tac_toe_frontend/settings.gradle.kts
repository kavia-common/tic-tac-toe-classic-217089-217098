pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.5.2"
        id("com.android.library") version "8.5.2"
        id("org.jetbrains.kotlin.android") version "1.9.24"
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-android" || requested.id.id == "org.jetbrains.kotlin.android") {
                useVersion("1.9.24")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "example-android-app"

// Conventional Gradle includes
include(":app")
include(":list")
include(":utilities")
