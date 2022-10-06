#!/bin/bash
#
# SEND-EMAIL, PYTHON SCRIPT HELPER
# updated: 2022-10-06 10:26
#
# NOTES:
# + A file "./info.sh.tmp" should exist and be able to perform set-export evironment variable "PY_EMAIL_PASSWORD".
# + All files ("./email-content.txt", "./email-subject.txt") required for email should be in place.
# + Proper email configuration should be pointed at by file "./conf.profile.ini"
#

function __cmd_str_helper {
    echo "--> "${cmd_str}
    eval ${cmd_str}
    echo -e '--> exit_code: '${?}'\n'
}

# set password env var
cmd_str="command source ./info.sh.tmp"
__cmd_str_helper

# make env var exportable
cmd_str="command declare -x PY_EMAIL_PASSWORD"
__cmd_str_helper

# execute python
cmd_str="command python send-email-v1.py"
__cmd_str_helper

# unset env var
cmd_str="command unset PY_EMAIL_PASSWORD"
__cmd_str_helper

