The CI linter expects a root-level `lint` task. This project uses Gradle Declarative DSL (.dcl),
so root lint may not be available. Use the helper script to run lint on the app module:

  ./gradlew-lint.sh

If your environment requires executable permissions, ensure:
  chmod +x gradlew-lint.sh
