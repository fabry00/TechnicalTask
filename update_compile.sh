#!/bin/bash

SITE=""
if [ -z "$1" ]
  then
    echo "No argument supplied. ARG0=y to perform mvn site or n only to pull and mvn install"
	exit
  else
    SITE=$1
fi
echo "-------------------------------------"
read -p "WARNING all the changes will be reverted " -n1 -s

git checkout .

echo "-------------------------------------"
echo " UPDATING FORM REPOSITORY"
git pull

echo "-------------------------------------"
read -p "Press any key to continue... " -n1 -s

echo "-------------------------------------"
echo "-------------------------------------"

echo "-------------------------------------"
echo "COMPILING COMMONS"
cd Commons 
mvn install
if [ $SITE == "y" ]
    then
	echo "-------------------------------------"
	echo "SITE COMMONS"
	  mvn site -Ddependency.locations.enabled=false
fi

echo "-------------------------------------"
read -p "Press any key to continue... " -n1 -s

echo "-------------------------------------"
echo "-------------------------------------"
echo "COMPILING Task1Service"
cd ../Task1 
mvn install
if [ $SITE == "y" ]
    then
	echo "-------------------------------------"
	echo "SITE Task1"
	  mvn site -Ddependency.locations.enabled=false
fi

echo "-------------------------------------"
read -p "Press any key to continue... " -n1 -s

echo "-------------------------------------"
echo "-------------------------------------"
echo "COMPILING Task2"
cd ../Task2 
mvn install
if [ $SITE == "y" ]
    then
	echo "-------------------------------------"
	echo "SITE Task1"
	  mvn site -Ddependency.locations.enabled=false
fi

echo "-------------------------------------"
echo "-------------------------------------"
cd ../
chmod a+x *.sh
echo "END"
