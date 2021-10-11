#! /bin/bash

echo -e '\n--> connect-wifi script v1.0 2021-10-10 <--\n'


w="wlp4s0"


function __helper_fn { echo $comm ; sleep 1 ; eval $comm ; echo ; sleep 1 ; }


comm="service avahi-daemon stop"
__helper_fn


comm="ip link set $w up"
__helper_fn


comm="ip link | grep $w"
__helper_fn


comm="iwlist $w scan | grep -E -i '(essid|busy|result)'"
__helper_fn


comm="wpa_supplicant -B -i $w -c /etc/wpa_supplicant/wpa_supplicant.conf"
__helper_fn


comm="iwconfig $w" 
__helper_fn


comm="dhclient -v $w"
__helper_fn


unset -f __helper_fn


