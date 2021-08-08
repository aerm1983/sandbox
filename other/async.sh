#!/bin/bash

clear
i=0
i_max=5
t=$i_max

function f1 {
    sleep 1
    echo "i:${i} ; t:${t}"
}

function f2 {
    while [ $i -lt $i_max ]
    do
        let t=i_max-i
        f1 &
        let i=i+1
    done

}

f2
jobs
time wait
