#!/usr/bin/env bash
cd $3;
sudo -u $1 git log << EOF
$2
EOF