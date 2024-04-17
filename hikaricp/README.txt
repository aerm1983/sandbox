See reminder comment in pom.xml

2024-04-17:
Default VM Args in launcher configuration:
-Xms8m
-Xmx128m
-Dspring.profiles.active=testPmRaceCondition
-Dcom.zaxxer.hikari.housekeeping.periodMs=6000

Non-recognized system properties are simply ignored, so this is feasible:
-D__ZZZ__com.zaxxer.hikari.housekeeping.periodMs=6000

