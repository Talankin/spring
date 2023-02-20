#!/bin/bash
u="tds"
#ps -U $u | wc -l | { read x; echo "User ${u} runs ${x} processes."; } >> /home/tds/work/cron.log 2>&1
ps -U $u | wc -l | { read x; logger "User ${u} runs ${x} processes."; }