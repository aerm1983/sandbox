#!/bin/bash



cat ./data.txt
echo -e "*** cat finished ***\n"



awk ' /.*/ { print $1 " : " $9 } ' ./data.txt
echo -e "*** awk finished 1 ***\n"



awk ' /^.*pty1.*bash$/ { print $1 " - " $2 " : " $9 }' ./data.txt
echo -e "*** awk finished 2 ***\n"



awk -f ./script.awk ./data.txt
echo -e "*** awk finished 3 ***"
