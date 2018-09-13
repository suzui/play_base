#!/usr/bin/env bash
cd $4;
sudo -u $1 -S ls << EOF
$2
EOF
sudo play id << EOF
$3
EOF
sudo -u $1 play deps --sync;
sudo -u $1 play stop;
sudo -u $1 play clean;
sudo -u $1 play start;
sudo -u $1 play precompile;