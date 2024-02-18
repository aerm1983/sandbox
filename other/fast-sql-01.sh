#! /bin/bash
# 2024-02-07 05:53

user='root'
host='localhost'
port='3306'
pass='x'
db='pre_mappingmanagement'


echo -e '\n-->query 1, test:'
mysql -B -u $user --password=$pass -h $host -P $port -D $db -e 'select 1 as my_integer;'


echo -e '\n-->query 2, insert:'
# i=${1}
i='10897' # 10947
while [ $i -lt '10947' ] ; do
    echo '--->i:'$i
    sql='USE pre_mappingmanagement; '
    sql=${sql}'INSERT INTO `condition_mapping` (`merchant_condition`, `marketplace_condition`, `merchant`, `marketplace`, `created_at`, `updated_at`, `store_id`, `category_id`)  '
    sql=${sql}"VALUES ('NEW', '1', 'do-commerce', 'amazon', '2024-02-07 06:06:00', '2024-02-07 06:06:00', 'do-commerce', "${i}"); "
    echo $sql
    mysql -B -u $user --password=$pass -h $host -P $port -D $db -e "${sql}"
    let 'i++'
    echo ''
done ;

