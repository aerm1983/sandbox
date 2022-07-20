#! /bin/bash

__line=$( jps -l | grep --color=never -P 'target.*mockup' )

echo 'jps:' ${__line}

__pid=$( echo ${__line} | awk -F ' ' '// { print $1 }' )

sentence='taskkill //f //pid '"${__pid}"

echo ${sentence}

eval ${sentence}

echo 'done!'
