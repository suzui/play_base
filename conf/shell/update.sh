#!/usr/bin/env bash
cd $3;
sudo -u $1 ls << EOF
$2
EOF
sudo -u $1 git checkout $4;
sudo -u $1 git pull;