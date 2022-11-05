#!/bin/bash
#
# SEND-EMAIL, PYTHON SCRIPT HELPER
# updated: 2022-10-06 10:26
#
# NOTES:
# + Optionally, set-export evironment variable "PY_EMAIL_PASSWORD", for security reasons.
# + All files ("conf.smtp.login.ini", "conf.email.ini", "email-content.txt") required for email should be in proper folder, and such into "tmp" folder.
#

function __cmd_str_helper {
    echo "--> "${cmd_str}
    eval ${cmd_str}
    echo -e '--> exit_code: '${?}'\n'
}

# make env var exportable # DEPRECATED
# cmd_str="command declare -x PY_EMAIL_PASSWORD"
# __cmd_str_helper

# execute python
cmd_str="command python ../lib/send-email-log-auditor-v2.py ${@}"
__cmd_str_helper

# unset env var # DEPRECATED
# cmd_str="command unset PY_EMAIL_PASSWORD"
# __cmd_str_helper

