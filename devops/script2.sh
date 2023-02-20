#!/bin/bash
watch "grep -E '^(Pid|PPid|VmRSS):' /proc/$1/status"