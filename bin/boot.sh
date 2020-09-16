#! /bin/sh
source /etc/profile
#通过脚本路径计算APP相关变量 11
if readlink -f "$0" > /dev/null 2>&1
then
  SHELL_BIN=$(readlink -f "$0")
else
  SHELL_BIN="$0"
fi
BIN_HOME=$(dirname $SHELL_BIN)
APP_HOME=$(dirname "$BIN_HOME")
GC_LOGS=/home/app/logs
PID_LOG=$APP_HOME/pid
APP_MAIN=com.example.demo.DemoApplication

#JVM启动参数
#-server:一定要作为第一个参数,在多个CPU时性能佳
#-Xloggc:记录GC日志,这里建议写成绝对路径,如此便可在任意目录下执行该shell脚本
JAVA_OPTS="-Duser.timezone=Asia/Shanghai -server -Xms1024m -Xmx1024m -Xmn512m -XX:SurvivorRatio=8 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOGS/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$GC_LOGS -XX:ErrorFile=$GC_LOGS/hs_err_pid%p.log"
#JVM类加载路径
CLASSPATH=.:$APP_HOME/config
#加载lib
CLASSPATH=$CLASSPATH:$(echo $APP_HOME/lib/*.jar|sed 's/ /:/g')
JAVA=`which java`

SERVICE_NAME="demo"


#打印环境变量
echo "======================================================================================"
echo "SHELL_BIN: "$SHELL_BIN
echo "BIN_HOME:  "$BIN_HOME
echo "APP_CONF   "$APP_CONF
echo "APP_HOME:  "$APP_HOME
echo "PID_LOG:   "$PID_LOG
echo "JAVA_HOME: "$JAVA_HOME
echo "JAVA_OPTS: "$JAVA_OPTS
echo "CLASSPATH: "$CLASSPATH
echo "======================================================================================"

start(){
echo "Starting As "
    if [ -f "$PID_LOG" ];then
        if kill -0 `cat "$PID_LOG"` > /dev/null 2>&1; then
             echo already running as process `cat "$PID_LOG"`.
             exit 0
        fi
    fi

    $JAVA $JAVA_OPTS -Duser.dir=$APP_HOME -classpath $CLASSPATH $APP_MAIN  > "$GC_LOGS/start.log" 2>&1 o

	if [ $? -eq 0 ];
    then
      if /bin/echo -n $! > "$PID_LOG"
      then
        sleep 1
        echo STARTED
          	echo "SUCCESS"
  			echo "application start success"
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo WOKRE DID NOT START
      exit 1
    fi
}

stop(){
    if [ -f "$PID_LOG" ];then
        pcnt=`jps -l | grep "$SERVICE_NAME"| wc -l`
        if [ $pcnt -eq 0 ]; then
            echo "process already stopped."
            rm -f $PID_LOG
            exit 0
        fi
        if [ -n "$(cat $PID_LOG)" ];then
            kill $(cat $PID_LOG)

            echo "check $SERVICE_NAME.service status..."
            for i in `seq 15`
            do
                pcnt=`ps $(cat $PID_LOG) | grep "$SERVICE_NAME" | wc -l`
                if [ $pcnt -eq 0 ]; then
                    break
                fi
                echo  ".\c"
                sleep 1
            done
            if [ $pcnt -ne 0 ]; then
               kill -9 $(cat $PID_LOG)
            fi
            rm -f $PID_LOG
            echo
            echo "stop success"
        else
            echo "[FAIL] PID FILE is empty, please check whether the application running, and use kill command to stop the application"
            exit 0
        fi
    else
        pid=`ps -ef | grep "$SERVICE_NAME" | awk 'NR==1{print $2}'`
        echo "PID file is not exist, ps check pid is $pid"
        if [ -n $pid ];then
            kill -9 $pid

            echo "check $SERVICE_NAME.service status..."
            for i in `seq 15`
            do
                pcnt=`ps $pid | grep "$SERVICE_NAME" | wc -l`
                if [ $pcnt -eq 0 ]; then
                    break
                fi
                echo  ".\c"
                sleep 1
            done
            if [ $pcnt -ne 0 ]; then
               kill -9 $pid
            fi
            echo "stop $pid success"
      fi
    fi
}

case "$1" in
start)
  start "$2"
  ;;
  stop)
  stop
  ;;
  *)
  echo 'Usage command: {start|stop}\n'
  exit 1
  ;;
esac

exit 0
