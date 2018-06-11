#!/usr/bin/env bash
cd $3;
sudo -u $1 git branch -va << EOF
$2
EOF