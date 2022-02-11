#! /bin/bash



# file to process ('./input' folder)
file='test.txt' # edit
echo "file to process: "${file}



# directory preparation
if test -d './output'
then 
    echo "directory './output' already exists"
else 
    mkdir -v './output'
fi
echo -e "\n"



# input, sequence to capture
grep --color=always -E 'old undisciplined ([a-z]+)' './input/'${file} # edit regular expression
echo -e "*** sequence to capture finished ***\n\n"



# replacement process
sed -Ef './script.sed' './input/'${file} > './output/'${file} # edit './script.sed'
echo -e "*** replacement process finished ***\n\n"



# output, replacement verification
grep --color=always -E 'brand new well[-]behaved [a-z]+' './output/'${file} # edit regular expression
echo -e "*** replacement verification finished ***"
