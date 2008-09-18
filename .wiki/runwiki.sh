#!/bin/bash

cd $(dirname $0)

export PYTHONPATH="$PWD:$PYTHONPATH"

# http://moinmo.in/
# http://moinmo.in/MoinMoinDownload
moin server standalone

