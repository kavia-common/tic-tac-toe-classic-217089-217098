#!/usr/bin/env bash
set -euo pipefail
# Forward lint/verification to a valid module task under Declarative Gradle.
# Some setups may not expose ':app:lint' directly; ':app:check' will run verification including lint.
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
exec "${DIR}/gradlew" :app:check
