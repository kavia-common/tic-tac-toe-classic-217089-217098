#!/usr/bin/env bash
set -euo pipefail

# Run Android Lint for the app module directly using the project's Gradle wrapper.
# This bypasses the need for a root-level 'lint' task which may not exist with Declarative Gradle.
REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/.. && pwd)"
APP_DIR="${REPO_DIR}/tic_tac_toe_frontend"

chmod +x "${APP_DIR}/gradlew" || true
exec "${APP_DIR}/gradlew" :app:lint
