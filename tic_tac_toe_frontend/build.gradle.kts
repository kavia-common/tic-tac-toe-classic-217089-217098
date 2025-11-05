import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 Minimal conventional Gradle root build file.
 Adds repositories for all projects to ensure dependency resolution.
*/

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("assembleDebug") {
    group = "build"
    description = "Convenience root task that depends on :app:assembleDebug if it exists."
    doFirst {
        val appProject = project.findProject(":app")
        val appAssemble = appProject?.tasks?.findByName("assembleDebug")
        if (appAssemble != null) {
            dependsOn(appAssemble)
        } else {
            logger.lifecycle("':app:assembleDebug' not found at execution time; root 'assembleDebug' will no-op.")
        }
    }
}

tasks.register("check") {
    group = "verification"
    description = "Root verification task; depends on :app:check if present."
    doFirst {
        val appProject = project.findProject(":app")
        val appCheck = appProject?.tasks?.findByName("check")
        if (appCheck != null) {
            dependsOn(appCheck)
        } else {
            logger.lifecycle("':app:check' not found at execution time; root 'check' will no-op.")
        }
    }
}
