#!/usr/bin/env bash
BD_BIN="${BASH_SOURCE-$0}"
BD_BIN="$(dirname "${BD_BIN}")"
BD_BIN="$(cd "${BD_BIN}"; pwd)"
BD_DIR="${BD_BIN}"
OUT_DIR="$BD_DIR/output"
echo $OUT_DIR
TG_DIR="$BD_DIR/target"
echo "Starting building "
profile=prod
if [ $# -eq 1 ]; then
    profile=$1
    echo $profile
fi
mvn clean package -P$profile -DskipTests;
if [ ! -d $OUT_DIR ]; then
    mkdir -p $OUT_DIR
fi
rm -rf $OUT_DIR/*;
cp $TG_DIR/*-assembly.zip $OUT_DIR/demo-assembly.zip;
cd $OUT_DIR/
unzip demo-assembly.zip

if [ ! "$(ls -v  $OUT_DIR)"  ] ; then
  echo "Building error"
  exit 1
fi

echo "Building finished"
