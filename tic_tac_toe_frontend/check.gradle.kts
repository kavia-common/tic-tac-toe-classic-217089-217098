import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 This Kotlin build script provides a root-level 'check' task for CI tools
 that expect it. The actual project uses Declarative Gradle (.dcl files),
 but some CI runners invoke 'gradle check' at the root.
*/

open class CheckForwardTask : DefaultTask() {
    init {
        group = "verification"
        description = "Forwards check to :app:check"
    }

    @TaskAction
    fun forward() {
        // No-op. The actual work is done via task dependencies.
    }
}

// Register a root-level 'check' task and forward to :app:check
tasks.register<CheckForwardTask>("check") {
    // Prefer the module task directly
    dependsOn(":app:check")
}
