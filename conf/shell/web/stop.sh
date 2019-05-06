#!/usr/bin/env bash
cd $3;
ls;
sudo -s -S << EOF
$2
EOF
sudo pm2 l;
sudo pm2 delete $4;