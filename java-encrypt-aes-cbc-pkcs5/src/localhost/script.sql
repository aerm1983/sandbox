USE pre_mailing;
 
 INSERT INTO account (
`id`,
`host`,
`port`,
`username`,
`password`,
`iv`,
`encode`
) VALUES (
'1',
'smtp.gmail.com',
'587', -- gmail 587, standard 25
'user@gmail.com',
'Dclev/b0SVV0dvN0Xfyd30001pvqXS++mkGzx4V3Nbw=',
( select UNHEX('A010666017630FF320A25A00886C0809') ),
( select UNHEX('EC4048294CCE044F01F70B5FC65609F40C65A2F6A30E3723CC0F00CD6E0C9862') )
)
;

 
 
USE pre_mailing;

UPDATE account
SET
`host` = 'smtp.gmail.com',
`port` = '587', -- gmail 587, standard 25
`username` = 'user@gmail.com',
`password` = 'fBy0slHQH00ISyssE9n0brOcZvpeJ8c0uSA20H00iZA=',
`iv` = ( select UNHEX('90530C90AE5B8FDB37FF0990EB00A2E4') ),
`encode` = ( select UNHEX('E04048294CCE344F10F79B54C056E9F4EC05A2F0730E3020CCAF00CD6E1C9862') )
WHERE TRUE
AND `id` = '1'
;
 
