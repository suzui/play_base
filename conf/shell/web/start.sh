#!/usr/bin/env bash
cd $3;
sudo -u $1 -S ls << EOF
$2
EOF
sudo cnpm i;
sudo pm2 start npm --name $4 -- run build$5;

