#!/bin/sh

PIDs=""

####AccountService
echo "******************** STARTING Task1 Service *************************"
sleep 1s
java -jar Task1/target/Task1-1.0-SNAPSHOT-launcher.jar server Task1/task1service.yml 30 &
PID=$!
echo "PID PrOCESS: $PID"
PIDs="$PIDs $PID "
####################################ààà
sleep 5s

echo "*************************************************************"
echo "******************** Task2 Service *************************"
sleep 1s
java -jar Task2/targetTask1-2.0-SNAPSHOT-launcher.jar server Task2/task2service.yml  50 &
PID=$!
echo "PID PrOCESS: $PID"
PIDs="$PIDs $PID "
####################################ààà
sleep 5s


echo "*************************************************************"
echo "*************************************************************"
echo "*************************************************************"

echo "All pids processes: $PIDs"
echo "" > "PIDs"
echo "$PIDs" >> "PIDs"
