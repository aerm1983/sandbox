#! /bin/bash

# example
# use sed to make a replacement in multiple files

pwd

file_list=$( ls './input' )
echo $file_list

rm -Rf './output'
mkdir -v './output'

for f in $file_list
do
    sed -E -f './script.sed' "./input/${f}" > "./output/${f}"
    echo "    ${f} processed"
done

echo 'done!'
