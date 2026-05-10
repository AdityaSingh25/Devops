#!/bin/bash
set -e

# 1. Start Postgres in the background using its official entrypoint script
# We pass the default 'postgres' command to it
/usr/local/bin/docker-entrypoint.sh postgres &

# 2. Wait for Postgres to be ready
echo "Waiting for Postgres to start..."
until pg_isready -h localhost -U "$POSTGRES_USER"; do
  sleep 1
done
echo "Postgres is ready!"

# 3. Start the Java Application
echo "Starting Java Application..."
java BackendServer
