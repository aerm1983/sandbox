# awk script, filter line with "pty" and "bash", print fields 1, 2 and 9
/^.*pty1.*bash$/ { print $1 " - " $2 " : " $9 }