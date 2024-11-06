#!/usr/bin/env sh

# Create new
curl -X POST -H 'Content-type: Application/json' -d '{"name": "test", "datetime": "2024-11-06T18:46:37Z"}' localhost:8080/

# List all
curl localhost:8080/

# Update (see that you get 404 when the object does not already exist)
curl -i  -X PUT -H 'Content-type: Application/json' -d '{"name": "a new name", "datetime": "2024-11-06T18:46:37Z"}' localhost:8080/b0d7b1c9-dbb5-4756-8415-2bcf304f81f5


