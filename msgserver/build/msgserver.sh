#!/bin/bash

nohup java -Dfile.encoding=UTF-8 -cp .:../lib/msgserver.jar com.jihuiduo.msgserver.starter.ServerStarter 1>/dev/null 2>&1 &
exit 0
