#!/bin/bash
# Run this script inside the lru-cache-project folder after extracting the zip.
# It initializes git and pushes the project to a new GitHub repo.

set -e

REPO_NAME="lru-cache-java"

echo "Initializing git repository..."
git init
git add .
git commit -m "Initial commit: LRU Cache implementation with HashMap + Doubly Linked List"

echo ""
echo "Now create a new repo on GitHub named '$REPO_NAME' (or any name you like):"
echo "  -> https://github.com/new"
echo ""
echo "Then run these commands (replace the URL with your repo's URL):"
echo ""
echo "  git branch -M main"
echo "  git remote add origin https://github.com/luna27/$REPO_NAME.git"
echo "  git push -u origin main"
echo ""
echo "Done! Once pushed, add the repo link to your resume/portfolio."
