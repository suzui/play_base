#!/usr/bin/env bash
cd $3;
ls;
sudo -u $1 git checkout $4 << EOF
$2
EOF
sudo -u $1 git pull << EOF
$2
EOF