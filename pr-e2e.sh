#!/bin/bash

URL="http://localhost:8080/trigger"
PAYLOAD="@app/src/test/resources/pull-request-webhook-payload.json"

# If this doesn't work, change permissions `chmod +x pr-e2e.sh`

curl -X POST \
    -H "Content-Type: application/json" \
    -H "X-GitHub-Event: pull_request" \
    -d "$PAYLOAD" \
    "$URL"
