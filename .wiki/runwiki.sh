#!/bin/bash

cd $(dirname $0)

export PYTHONPATH="$PWD:$PYTHONPATH"

moin server standalone

