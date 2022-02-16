#! /bin/bash

echo -e '\n--> wifi-connect  v_2022-02-14_03:00 <--\n'


w="wlp4s0"


function __helper_fn { echo '--->'"${cmd}" ; sleep 1 ; eval "${cmd}" ; echo ; sleep 1 ; }


cmd="service avahi-daemon stop"
__helper_fn


cmd="service avahi-daemon status"
__helper_fn


cmd="rfkill unblock wlan"
__helper_fn


cmd="rfkill"
__helper_fn


cmd="ip link set $w down"
__helper_fn


cmd="ip link set $w up"
__helper_fn


cmd="ip link | grep --color=always '${w}'"
__helper_fn


sleep 5


cmd="iwlist $w scan | grep --color=always -P -i '(essid|busy|result)'"
__helper_fn


# wifi password configuration setting is required only once
# cmd="echo 'my-wifi-password' | wpa_passphrase 'my-wifi-signal-id' > /etc/wpa_supplicant/wpa_supplicant.conf"
cmd="cat /etc/wpa_supplicant/wpa_supplicant.conf"
__helper_fn


cmd="wpa_supplicant -B -i $w -c /etc/wpa_supplicant/wpa_supplicant.conf"
__helper_fn


cmd="iwconfig $w" 
__helper_fn


cmd="dhclient -v $w"
__helper_fn


sleep 3 


cmd="ping -c4 www.google.com"
__helper_fn


cmd="service avahi-daemon restart"
__helper_fn


cmd="service avahi-daemon status"
__helper_fn


unset -f __helper_fn


echo -e "done!\n"




