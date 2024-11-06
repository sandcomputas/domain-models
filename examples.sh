#!/usr/bin/env sh

# Create new
curl -X POST -H 'Content-type: Application/json' -d '{"id": "EE3D6B70-E0C9-4E70-A91F-3A8120DD4F7A", "name": "test"}' localhost:8080/

# List all
curl localhost:8080/

# Update (see that you get 404 when the object does not already exist)
curl -i  -X PUT -H 'Content-type: Application/json' -d '{"name": "a new name"}' localhost:8080/b0d7b1c9-dbb5-4756-8415-2bcf304f81f5


