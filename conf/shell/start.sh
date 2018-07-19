#!/usr/bin/env bash
cd $3;
sudo -u $1 ls << EOF
$2
EOF
sudo -u $1 play deps --sync;
sudo -u $1 play clean;
sudo -u $1 play start;
sudo -u $1 play precompile;