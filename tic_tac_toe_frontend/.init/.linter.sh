#!/usr/bin/env bash
set -euo pipefail

# This script is invoked by CI to run lint. Some CI environments expect a root-level "lint" task.
# Our project primarily uses Declarative Gradle (.dcl), which may not expose a root lint task.
# We forward the lint command to the app module instead.

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/.. && pwd)"

# Ensure gradlew is executable
chmod +x "${DIR}/gradlew" || true

# Prefer module lint task
exec "${DIR}/gradlew" :app:lint
