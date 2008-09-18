#!/bin/bash

cd $(dirname $0)

. .functions.sh

echo "Preparing data..."
deleteData
unpackData

echo "Starting WIKI..."
runWIKI $1

echo "Postprocessing..."
./update_archive.sh
deleteData

echo "Done!"

