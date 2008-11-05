#!/bin/bash

function prepareRuntimeData() {
	tar xf data.tar
	tar xf underlay.tar
}

function deleteRuntimeData() {
	if [ -d data ]; then rm -rf data; fi
	if [ -d underlay ]; then rm -rf underlay; fi
}

function createDataArchive() {
	basedir=$(realpath .)
	workdir=$(mktemp -d)
	cp -abR data $workdir/
	cd $workdir
	find data -type d -name cache | xargs rm -rf
	find data -type f -name "*pyc" -delete
	find data -type f | sort -u | xargs tar cf $basedir/data.tar
	cd $basedir
	rm -rf $workdir
}

function runWIKI() {
	export PYTHONPATH="$PWD:$PYTHONPATH"
	# http://moinmo.in/
	# http://moinmo.in/MoinMoinDownload
	moin.sh server standalone --config-dir=. --interface=localhost --port=10086 $* > moin.log 2>&1
}
