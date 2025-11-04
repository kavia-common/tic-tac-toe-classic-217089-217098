This directory contains helper scripts used by CI to run lint and similar checks in a project
that uses Gradle Declarative DSL (.dcl). Traditional root tasks like `lint` may not exist at
the root project level. 

Use the provided linter shim:
  ./.init/.linter.sh

It forwards to the Android app module lint task:
  ./gradlew :app:lint
