#!/bin/bash

currentPath=`dirname $0`
#取得当前文件所在目录的绝对路径
if [ ${currentPath:0:1} = "." ];then
	currentPath=`pwd`
elif [ ! ${currentPath:0:1} = "/" ];then
	currentPath=`pwd`"/"`dirname $0`
fi
cd $currentPath
#ulimit -n 65535
	nohup java -Dfile.encoding=UTF-8 -cp $currentPath/../lib/netproxy.jar com.jihuiduo.netproxy.start.ServerStart 1>>/dev/null 2>&1 &
exit 0
