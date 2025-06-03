#!/bin/bash

# If this doesn't work, change permissions `chmod +x start-e2e-docker-container.sh`

echo "Starting docker-compose.e2e test."

# Stop all containers
docker compose -f docker-compose.e2e.yml down -v --remove-orphans

# Prune images, volumes, builders...
docker image prune -af
docker volume prune -f
docker builder prune -af

# Build without cache
docker compose -f docker-compose.e2e.yml build --no-cache

# Start the containers
docker compose -f docker-compose.e2e.yml up
