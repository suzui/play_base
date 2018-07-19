#!/usr/bin/env bash
cd $3;
su $1 << EOF
$2
EOF
play deps --sync;
play clean;
play start;
play precompile;