#! /bin/bash

w="wlp4s0"


function __helper_fn { echo $comm ; sleep 1 ; eval $comm ; echo ; sleep 1 ; }


comm="ip link set $w up"

__helper_fn



comm="ip link"

__helper_fn



comm="iwlist $w scan | grep -i essid"

__helper_fn



comm="wpa_supplicant -B -i $w -c /etc/wpa_supplicant/wpa_supplicant.conf"

__helper_fn



comm="iwconfig $w" 

__helper_fn



comm="dhclient $w"

__helper_fn


unset -f __helper_fn


