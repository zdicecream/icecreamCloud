#!/bin/bash
# sh start.sh stop	关闭正在运行
# sh start.sh		直接启动
# sh start.sh sit	指定配置文件(sit)启动
#后台运行jar包 这里替换为你的jar包列表
jar_arr=(authority.jar icecream.jar dispatch.jar)
 
start(){
	stop
	
	yaml_name="prd"
	if [ ${1} ]
	then
		yaml_name=${1}
	fi
	echo ${yaml_name}
	
	for jar_name in ${jar_arr[@]};
	do
	
		#启动jar包
		if [ ${yaml_name} == "sit" ]
		then
			#测试环境
			nohup java -Dloader.path=./lib  -jar -Dspring.profiles.active=sit ${jar_name} >./out/${jar_name}.out 2>&1 &
		elif [ ${yaml_name} == "dev" ]
		then
			#开发环境
			nohup java -Dloader.path=./lib  -jar -Dspring.profiles.active=dev ${jar_name} >./out/${jar_name}.out 2>&1 &
		else
			#正式环境
			nohup java -Dloader.path=./lib  -jar ${jar_name} >./out/${jar_name}.out 2>&1 &
		fi
		echo ${yaml_name} ${jar_name} start
	done
	echo "启动完成"
}
 
stop(){
	for jar_name in ${jar_arr[@]};
		do
		#杀掉正在运行的进程
			tpid=`ps -ef|grep $jar_name|grep -v grep|grep -v kill|awk '{print $2}'`
			if [ ${tpid} ]
			then
				echo ${jar_name} ${tpid} kill
				kill -9  ${tpid}
			fi
		done
	echo "kill process success"
}
 
 
if [ !${1} -a ${1} == "start" ]
then
	start ${2}
elif [ ${1} == "stop" ]
then
	stop
else
	start ${1}
fi
