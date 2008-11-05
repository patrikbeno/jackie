#!/bin/bash

cd $(dirname $0)

. .functions.sh

echo "Preparing data..."
if [ -d data ]; then
	fname="data_$(date +%Y%m%d_%H%M).tgz"
	echo "!!! data directory exists; backing up: $fname"
	tar czf $fname data
fi
deleteRuntimeData
prepareRuntimeData

echo "Starting WIKI..."
runWIKI $*

echo "Postprocessing..."
createDataArchive
deleteRuntimeData

echo "Done!"

