#!/usr/bin/env bash
cd $3;
ls;
echo $2 | sudo -u $1 -S git checkout $4;
sudo -u $1 git pull << EOF
$2
EOF