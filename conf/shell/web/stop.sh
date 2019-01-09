#!/usr/bin/env bash
cd $3;
ls;
echo $2 |sudo -S -s;
sudo lsof -i:$4 |awk 'NR==2{print $2}' |xargs sudo kill -9;
