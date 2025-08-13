#!/usr/bin/env bash
set -Eeuo pipefail

# Creates a PR for the changelog update in this repo.
# Requirements:
#  - git configured with write access to the 'origin' remote
#  - GitHub CLI (gh) authenticated: gh auth status
#
# Usage:
#  ./scripts/create_pr.sh
#  BRANCH_NAME=my-branch ./scripts/create_pr.sh
#  BASE_BRANCH=main ./scripts/create_pr.sh

command -v git >/dev/null 2>&1 || { echo "git is required"; exit 1; }
if ! command -v gh >/dev/null 2>&1; then
  echo "gh (GitHub CLI) not found. Install from https://cli.github.com and run 'gh auth login'."
  exit 1
fi

# Determine default base branch
DEFAULT_BASE="$(git remote show origin 2>/dev/null | sed -n 's/.*HEAD branch: //p' || true)"
BASE_BRANCH="${BASE_BRANCH:-${DEFAULT_BASE:-main}}"

# Generate a branch name if not provided
STAMP="$(date +%Y%m%d-%H%M)"
BRANCH_NAME="${BRANCH_NAME:-docs/changelog-unreleased-${STAMP}}"

PR_TITLE="${PR_TITLE:-docs(changelog): update Unreleased with recent changes}"
PR_BODY="${PR_BODY:-This PR updates the Unreleased section in CHANGELOG.md with recent additions and changes.}"

# Ensure local state is up to date
git fetch origin --prune

# Create branch from base
git checkout -B "${BRANCH_NAME}" "origin/${BASE_BRANCH}" || git checkout -b "${BRANCH_NAME}"

# Stage changelog if modified
if ! git status --porcelain | grep -q 'CHANGELOG\.md'; then
  echo "No unstaged changes to CHANGELOG.md detected. Continuing anyway..."
else
  git add CHANGELOG.md
fi

# Commit if there are staged changes
if git diff --cached --quiet; then
  echo "No staged changes to commit. If this is unexpected, ensure CHANGELOG.md was modified."
else
  git commit -m "docs(changelog): add recent updates to Unreleased"
fi

# Push branch
git push -u origin "${BRANCH_NAME}"

# Create PR
if gh pr view "${BRANCH_NAME}" >/dev/null 2>&1; then
  echo "PR for branch ${BRANCH_NAME} already exists:"
  gh pr view "${BRANCH_NAME}" --web
else
  gh pr create --title "${PR_TITLE}" --body "${PR_BODY}" --base "${BASE_BRANCH}" --head "${BRANCH_NAME}"
fi