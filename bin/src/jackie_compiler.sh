#!/bin/bash

cd $(dirname $0)

java -classpath "$(cat .classpath)" org.jackie.tools.Main "$*"
