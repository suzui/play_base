#!/usr/bin/env bash
cd $3;
ls;
echo '$2' |su $1;
lsof -i:$4 |awk 'NR==2{print $2}' |xargs kill -9;
npm install;
npm run build;
