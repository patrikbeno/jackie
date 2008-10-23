#!/bin/bash

function deleteCaches() {
	find data -type d -name "cache" | xargs rm -rf
	find -type f -name "*.pyc" | xargs rm
}

function deleteCompiledCode() {
	find -type f -name "*.pyc" | xargs rm
}

function deleteData() {
	rm -rf data
}

function unpackData() {	
	tar xf data.tar
}

function packData() {	
	find data -type f | sort | xargs tar cf data.tar
}

function runWIKI() {
	export PYTHONPATH="$PWD:$PYTHONPATH"
	# http://moinmo.in/
	# http://moinmo.in/MoinMoinDownload
	moin server standalone $1
}
