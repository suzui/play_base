#!/usr/bin/env bash
cd $4;
sudo -u $1 -S ls << EOF
$2
EOF
sudo play id $3;
sudo -u $1 rm -rf /home/$1/.ivy2/cache/customerModules;
sudo -u $1 play deps --sync;
sudo -u $1 play clean;
sudo -u $1 play start;
sudo -u $1 play precompile;