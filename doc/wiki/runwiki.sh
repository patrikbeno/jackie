#!/bin/bash

cd $(dirname $0)

echo "Starting WIKI..."
# http://moinmo.in/
# http://moinmo.in/MoinMoinDownload
/usr/share/moin/server/moin server standalone --config-dir=. --interface=localhost --port=10086 $*

echo "Deleting runtime data..."
find data -type d -name cache | xargs rm -rf
find data -type f -name "*pyc" -delete

echo "Done!"

