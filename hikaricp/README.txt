See reminder comment in pom.xml

2024-04-17:
Default VM Args in launcher configuration:
-Xms8m
-Xmx128m
-Dspring.profiles.active=testPmRaceCondition
-Dcom.zaxxer.hikari.housekeeping.periodMs=6000

Non-recognized system properties are simply ignored, so this is feasible:
-D__ZZZ__com.zaxxer.hikari.housekeeping.periodMs=6000



2024-04-17:

Heidy SQL testing queries:



--
-- MAIN TEST springboot hikari-cp
--
USE `00_test_large_table`;
-- SELECT * FROM publish_error WHERE origin_uuid LIKE '%-0000-%' ; -- 21.0 secs
SELECT * FROM publish_error WHERE origin_uuid = '00000000-0000-0000-0000-000000000000' ; -- 9.2 secs
DELETE FROM publish_error WHERE origin_uuid = '00000000-0000-0000-0000-000000000000' ; -- 12.8 secs
UPDATE publish_error set updated_at = NOW() WHERE id = 23861352 ; -- 0 secs
SELECT * FROM publish_error WHERE id =  23861352 ; -- 0 secs



--
-- OTHER TEST
--

USE `00_test_large_table`;

INSERT INTO `publish_error` (
`product_site_id`, `origin_uuid`, `error_code`, 
`error_message`, `line_number`, `marketplace`, 
`created_at`, `updated_at`
) VALUES (
310044, '00000000-0000-0000-0000-000000000001', '409', 
'CONFLICT', 0, 'amazon', 
NOW(), NOW()
) ; -- 0 secs


UPDATE `publish_error` 
SET `error_code` = '510', `marketplace` = 'prestashop', `error_message` = 'CHANGED',
`line_number` = '10',  `created_at` = NOW(), `updated_at` = NOW() 
WHERE origin_uuid = '00000000-0000-0000-0000-000000000001' ; -- 12.8 secs


SELECT * FROM publish_error WHERE origin_uuid = '00000000-0000-0000-0000-000000000001' ; -- 9.2 secs

DELETE FROM publish_error WHERE origin_uuid = '00000000-0000-0000-0000-000000000001' ; -- 12.8 secs





