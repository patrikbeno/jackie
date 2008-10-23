#!/bin/bash

mydir=$(realpath $(dirname $0))

java -classpath "$(cat $mydir/.classpath)" org.jackie.tools.Shell $*
