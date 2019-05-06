#!/usr/bin/env bash
cd $3;
ls;
echo '$2' |sudo -S -s;
sudo pm2 delete $4;
sudo cnpm i;
sudo pm2 start npm --name $4 -- run build$5;