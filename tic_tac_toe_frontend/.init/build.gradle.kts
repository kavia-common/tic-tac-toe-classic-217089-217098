import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 This script provides a root-level 'lint' task as a CI placeholder.
 The actual Android project is configured using Declarative Gradle (.dcl files).
*/

open class LintForwardTask : DefaultTask() {
    init {
        group = "verification"
        description = "Forwards lint to :app:lint"
    }

    @TaskAction
    fun forward() {
        // No-op at execution time; dependency handles execution.
    }
}

tasks.register<LintForwardTask>("lint") {
    // Try to depend on module lint task
    dependsOn(":app:lint")
}
