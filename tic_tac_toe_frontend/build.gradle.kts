import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/*
 This Kotlin build script exists solely to provide a root-level 'lint' task for CI tools
 that expect it. The actual project config uses Declarative Gradle (.dcl files).
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
    // Ensure Gradle executes :app:lint when 'lint' is invoked at root
    dependsOn(gradle.includedBuilds.flatMap { it.task(":app:lint") })
    // Fallback: if not using composite builds, depend on module task directly
    dependsOn(":app:lint")
}
