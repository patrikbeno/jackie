#!/bin/bash

rm -rf ~/var/build/mvn/*
mvn -Dbuild.isolate install 
mvn -Dbuild.isolate -Dall install

