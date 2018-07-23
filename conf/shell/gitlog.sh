#!/usr/bin/env bash
cd $3;
sudo -u $1 -S ls << EOF
$2
EOF
git log;