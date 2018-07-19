#!/usr/bin/env bash
cd $3;
su $1 << EOF
$2
EOF
ps -p `less server.pid`;