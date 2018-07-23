#!/usr/bin/env bash
cd $3;
sudo -u $1 -S ps -p `less server.pid` << EOF
$2
EOF
