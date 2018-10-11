@echo off
SET DEVELOPMENT_HOME= C:\Users\IN00550\Desktop\Nerium_SVN
cd %DEVELOPMENT_HOME%
call  mvn package
call  mvn exec:java
PAUSE
