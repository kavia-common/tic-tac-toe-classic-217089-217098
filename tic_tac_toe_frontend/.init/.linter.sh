#!/usr/bin/env bash
set -euo pipefail
# Root-level linter shim: use the provided helper script to run module lint.
# This project uses Declarative Gradle (.dcl), so root 'lint' may not exist.

# Project root for this container is the parent of this .init directory
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Ensure scripts are executable
chmod +x "${PROJECT_ROOT}/gradlew-lint.sh" || true
chmod +x "${PROJECT_ROOT}/gradlew" || true

# Delegate to the helper which forwards to :app:lint
exec "${PROJECT_ROOT}/gradlew-lint.sh"
