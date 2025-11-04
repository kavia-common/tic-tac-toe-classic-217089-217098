#!/usr/bin/env bash
set -euo pipefail
# Forward lint invocation to the app module where Android lint tasks reside.
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
exec "${DIR}/gradlew" :app:lint
