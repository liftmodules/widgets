#!/bin/bash
 
# Automatically publish snapshots for branches pushed to master.

if [[ "${TRAVIS_PULL_REQUEST}" == "false" &&
      "${TRAVIS_BRANCH}" == "master" &&
      $(cat build.sbt) =~ "-SNAPSHOT"
]]; then
    sbt +publish
fi
