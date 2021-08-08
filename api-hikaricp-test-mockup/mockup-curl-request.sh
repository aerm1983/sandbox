#!/bin/bash

clear;
i_max=1    # serial repetitions in f_single_process
j_max=20   # parallel processes running asynchronously


function f_single_process {
    i=0;
    while [ $i -lt $i_max ]
    do
        echo "f_single_process, j:$j , i:$i"
        url="http://localhost:8020/api-issuer-mockup/map?i=${i}&j=${j}"
        command="curl -s GET '${url}'"
        echo $command 
        eval $command
        echo 
        let i=i+1
    done
}


function f_parallel_process {
    j=0;
    while [ $j -lt $j_max ]
    do
        echo "f_parallel_process, j:$j"
        f_single_process &
        let j=j+1
    done

}


f_parallel_process
echo "end of script"

