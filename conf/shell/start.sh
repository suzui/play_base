#!/usr/bin/env bash
cd $3;
ls;
sudo -s -S << EOF
$2
EOF
sudo play id -S << EOF
$4
EOF
sudo -u $1 rm -rf /home/$1/.ivy2/cache/customerModules;
sudo -u $1 play deps --sync;
sudo -u $1 play clean;
sudo -u $1 play start;
sudo -u $1 play precompile;