# CI Runner Usage Guide<br>`Totally Integrated Testing System`

## Overview

`Totally Integrated Testing System` is a JAVA based `CI runner` which fetches
the specified repository, runs the `Gradle` build and responds to the
provided `Github Webhook` notifying whether the merge can be done.<br>
Prerequisite for merging is that the build and the overall pipeline have
finished successfully.

## Adding a `pipeline.yml`

The `pipeline.yml` follows `Bitbucket` style convention for structuring steps.<br>
The most important section is the `pipelines` where the following convention must be followed:<br>
```
pipelines:
  branches:
    master:
      steps:
        - *build-master
    uat:
      steps
        - *build-uat
    '**':
      steps:
        - *build

  pull-requests:
    hotfix:
      steps:
        - *build-hotfix
    '**':
      steps:
        - *build
```

The `branches` and `pull-requests` sub-sections must be defined and **at least**<br>
the `** (wildcard)` with fallback step.

## File structure and syntax

## What Triggers the Runner

The Runner is triggered by calling the `/trigger` endpoint. It expects
`Github Webhook` JSON payload in order to process the build check. <br>
The runner will, based on the provided JSON payload, try to fetch the code
from remote repository and run it in a separate process.<br>
It will respond to `Github Webhook`, either allowing or preventing the
potential merge for the given commit.

When is pipeline triggered
1. Direct push to branch
2. Merge PR to branch
3. Open PR for branch
4. New Commit to PR

## Merge Policy
The following rules are applied before merging:
1. Restrict merge to branch if pipeline doesn't pass
2. Each commit will trigger the pipeline (build check is performed per commit basis)
3. Push to branch is allowed at any moment (potential commit that fixes the issue)


## Build Results

## Examples

## Misc
