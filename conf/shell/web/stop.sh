#!/usr/bin/env bash
cd $3;
ls;
sudo -s -S ;<< EOF
$2
EOF
lsof -i:$4 |awk 'NR==2{print $2}' |xargs kill -9;
