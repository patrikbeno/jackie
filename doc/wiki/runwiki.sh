#!/bin/bash

cd $(dirname $0)

echo "Starting WIKI..."
# http://moinmo.in/
# http://moinmo.in/MoinMoinDownload
moin server standalone --config-dir=. --interface=localhost --port=8080 $*

./cleanup.sh

echo "Done!"

