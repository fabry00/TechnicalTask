#!/bin/sh
filename="PIDs"

PIDs=""
while read -r line
do
    PIDs="$line"    
done < "$filename"

echo "STOPPING $PIDs"
kill -9 $PIDs