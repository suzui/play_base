#!/usr/bin/env bash
cd $3;
ls;
echo '$2' |sudo -s;
sudo pm2 delete $4;