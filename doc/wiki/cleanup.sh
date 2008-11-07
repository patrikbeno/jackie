#!/bin/bash

cd $(dirname $0)

echo "Deleting runtime data..."
find data -type d -name cache | xargs rm -rf
find data -type f -name "*pyc" -delete
