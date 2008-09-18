#!/bin/bash

cd $(dirname $0)

. .functions

echo "Preparing data..."
deleteData
unpackData

echo "Starting WIKI..."
runWIKI

echo "Postprocessing..."
./update_archive.sh
deleteData

echo "Done!"

