#!/bin/bash

cd $(dirname $0)

. .functions

echo "Cleaning up runtime data..."
deleteCaches 2> /dev/null
deleteCompiledCode 2> /dev/null

echo "Updating data archive..."
packData

echo "Done!"

