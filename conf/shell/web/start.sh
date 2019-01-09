#!/usr/bin/env bash
cd $3;
ls;
echo '$2' |su $1;
cnpm install;
npm run build;
