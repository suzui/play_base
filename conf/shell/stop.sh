#!/usr/bin/env bash
cd $3;
sudo -u $1 ls << EOF
$2
EOF
sudo -u $1 play stop;