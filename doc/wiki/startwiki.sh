#!/bin/bash

cd $(dirname $0)

moin server standalone --config-dir=. --interface jackie.greenhorn.sk --port 10086 --start $*
