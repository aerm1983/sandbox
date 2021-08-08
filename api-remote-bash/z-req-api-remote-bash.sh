#!/bin/bash

clear;

url='http://localhost:8020/api-remote-bash'

request='POST'

data="bash -c 'pwd' "

headers_all='
 --header "content-type: application/json"
 --header "x-header-field: 1234"
'

command="curl -s -i \n\n --request $request \n\n $headers_all \n\n --data $data \n\n $url \n"

echo -e $command

echo

eval $command

echo

echo "end of script"
