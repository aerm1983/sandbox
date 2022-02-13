#! /bin/bash

echo -e '\n--> wifi-connect  v_2022-02-13_11:00 <--\n'


w="wlp4s0"


function __helper_fn { echo "--->" $cmd ; sleep 1 ; eval $cmd ; echo ; sleep 1 ; }


cmd="service avahi-daemon stop"
__helper_fn


cmd="service avahi-daemon status"
__helper_fn


cmd="ip link set $w down"
__helper_fn


cmd="ip link set $w up"
__helper_fn


cmd="ip link | grep --color=always '${w}'"
__helper_fn


cmd="iwlist $w scan | grep --color=always -P -i '(essid|busy|result)'"
__helper_fn


cmd="wpa_supplicant -B -i $w -c /etc/wpa_supplicant/wpa_supplicant.conf"
__helper_fn


cmd="iwconfig $w" 
__helper_fn


cmd="dhclient -v $w"
__helper_fn


sleep 2


cmd="ping -c 4 www.google.com"
__helper_fn


cmd="service avahi-daemon restart"
__helper_fn


cmd="service avahi-daemon status"
__helper_fn


unset -f __helper_fn


echo "done!\n"


