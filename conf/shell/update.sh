#!/usr/bin/env bash
cd $3;
ls;
sudo -u $1 git checkout $4;
sudo -u $1 git pull;