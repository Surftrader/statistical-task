@echo off
javac App.java
(echo Main-Class: App) > MANIFEST.MF
jar cfm App.jar MANIFEST.MF App.class
java -jar App.jar %1
pause
