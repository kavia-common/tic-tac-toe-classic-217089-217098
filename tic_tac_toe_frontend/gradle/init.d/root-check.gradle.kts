import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 Init script to ensure a root-level 'check' task exists for CI environments
 that invoke 'gradle check' at the project root. This forwards to ':app:check'.
*/

open class CheckForwardTask : DefaultTask() {
    init {
        group = "verification"
        description = "Forwards check to :app:check (registered via init script)"
    }
    @TaskAction
    fun forward() {
        // No-op; dependency performs the actual work
    }
}

gradle.rootProject {
    // Register only if not already present
    if (tasks.findByName("check") == null) {
        tasks.register("check", CheckForwardTask::class.java) {
            dependsOn(":app:check")
        }
    }
}
