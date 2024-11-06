#!/usr/bin/env sh

# Create new
curl -X POST -H 'Content-type: Application/json' -d '{"id": "EE3D6B70-E0C9-4E70-A91F-3A8120DD4F7A", "name": "test"}' localhost:8080/

# List all
curl localhost:8080/

