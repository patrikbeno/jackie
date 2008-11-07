#!/bin/bash
#
# On http://jackie.greenhorn.sk there is a running JackieWiki instance.
# This script downloads current (live & running) wiki data directory and updates local data.tar
# Finally, commits archive into local bazaar branch
#

cd $(dirname $0)

. .functions.sh

# If data directory exists (data.tar is expanded), wiki is possibly running or this means conflict that needs human intervention. 
# Cannot continue.
if [ -d data ]; then
	echo "Directory 'data' exists! is WIKI running?"
	exit -1
fi

# rename (backup) existing data.tar if needed
if [ -f data.tar ]; then
	bakfile="data_$(date +%Y%m%d_%H%M).tar"
	echo "Backing up existing data.tar -> $bakfile"
	mv data.tar $bakfile
fi

# now get data from server
echo "Downloading WIKI data from greenhorn.sk"
ssh jackie@greenhorn.sk tar czf - -C /home/jackie/trunk/doc/wiki data | gunzip > data.tar

# filter out runtime stuff and build data.tar archive
echo "Filtering & archiving"
tar xf data.tar
createDataArchive
deleteRuntimeData

if [ "$1" == "--commit" ]; then
	echo "Committing data.tar"
	bzr ci -m "[doc] wiki data update" data.tar
fi
