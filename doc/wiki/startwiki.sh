#!/bin/bash

cd $(dirname $0)

./runwiki.sh --interface jackie.greenhorn.sk --port 10086 --start $*
