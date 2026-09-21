#!/usr/bin/env sh
set -eu

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
GRADLE_VERSION=8.9
DIST_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"
CACHE_DIR="${GRADLE_USER_HOME:-$HOME/.gradle}/wrapper/dists/gradle-${GRADLE_VERSION}-bin"
INSTALL_DIR="$CACHE_DIR/gradle-${GRADLE_VERSION}"
ZIP_FILE="$CACHE_DIR/gradle-${GRADLE_VERSION}-bin.zip"

if [ ! -x "$INSTALL_DIR/bin/gradle" ]; then
  mkdir -p "$CACHE_DIR"
  if [ ! -f "$ZIP_FILE" ]; then
    echo "Downloading Gradle ${GRADLE_VERSION}..."
    if command -v curl >/dev/null 2>&1; then
      curl -fL "$DIST_URL" -o "$ZIP_FILE"
    elif command -v wget >/dev/null 2>&1; then
      wget -O "$ZIP_FILE" "$DIST_URL"
    else
      echo "curl or wget is required to download Gradle." >&2
      exit 1
    fi
  fi
  rm -rf "$INSTALL_DIR.tmp"
  mkdir -p "$INSTALL_DIR.tmp"
  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ZIP_FILE" -d "$INSTALL_DIR.tmp"
  else
    echo "unzip is required to extract Gradle." >&2
    exit 1
  fi
  mv "$INSTALL_DIR.tmp/gradle-${GRADLE_VERSION}" "$INSTALL_DIR"
  rm -rf "$INSTALL_DIR.tmp"
fi

exec "$INSTALL_DIR/bin/gradle" "$@"
