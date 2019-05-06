#!/usr/bin/env bash
cd $3;
ls;
sudo -s -S << EOF
$2
EOF
sudo pm2 l;
sudo cnpm i;
sudo pm2 start npm --name $4 -- run build$5;

