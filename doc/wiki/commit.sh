#!/bin/bash
cd $(dirname $0)
./stopwiki.sh
bzr commit -m "[doc] wiki data update" data
