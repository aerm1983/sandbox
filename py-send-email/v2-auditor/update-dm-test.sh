#! /bin/bash

source_dir='../'$( basename $( pwd ) )

target_dir=${HOME}/Desktop/dm-test/log-health-auditor/usr

command rm -vfR ${target_dir}/py-send-email

command mkdir -vp ${target_dir}

cmd_str='command cp -vr '${source_dir}' '${target_dir}

echo '--> '${cmd_str}

eval ${cmd_str}

command mv -v ${target_dir}/$( basename $( pwd ) ) ${target_dir}/py-send-email
