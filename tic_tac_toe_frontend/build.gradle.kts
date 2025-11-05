import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 This Kotlin build script exists to provide root-level CI-friendly tasks for tools
 that expect them. The actual project config uses Declarative Gradle (.dcl files).
*/

open class LintForwardTask : DefaultTask() {
    init {
        group = "verification"
        description = "Forwards lint to :app:lint"
    }

    @TaskAction
    fun forward() {
        // No-op at execution time. We simply depend on :app:lint.
    }
}

tasks.register<LintForwardTask>("lint") {
    // Fallback: depend on module task directly
    dependsOn(":app:lint")
}

/**
 * Root-level 'check' task forwarding to ':app:check' so CI can run 'gradle check'.
 */
open class CheckForwardTask : DefaultTask() {
    init {
        group = "verification"
        description = "Forwards check to :app:check"
    }

    @TaskAction
    fun forward() {
        // No-op; the dependency does the actual work.
    }
}

tasks.register<CheckForwardTask>("check") {
    dependsOn(":app:check")
}
