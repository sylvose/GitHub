cls
@echo off

rem ---------------------------------------------------------------------------
rem   Building script for the Rest-1.0-SNAPSHOT.war
rem
rem   Environment Variable Prequisites
rem
rem
rem   JAVA_HOME       Must point at your Java Development Kit installation.
rem
rem   JAVA_OPTS       (Optional) Java runtime options 
rem ---------------------------------------------------------------------------


@rem --- Set user-defined variables. ------------------------------------------
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.7.0_21
set JAVA_OPTIONS=-server -Xms512m -Xmx1024m -XX:PermSize=512m -XX:MaxPermSize=512m

set MAVEN_HOME=C:\Users\Sylvose ALLOGO\apache-maven-3.0.4
set MAVEN_OPTS=-Xms512m -Xmx1024m -XX:PermSize=512m -XX:MaxPermSize=512m
@rem --------------------------------------------------------------------------


REM ---------------------------------------------------------------------------
REM    Rest Project
REM    If you plan to write automated Rest regularly then it might be a good idea to install.
REM    This allows you to easily build Rest.
REM 
REM
REM    See the NOTICE file distributed with this work for additional
REM    information regarding copyright ownership.
REM
REM    © Copyright  (c) – Propriété de Sylvose ALLOGO 
REM    All rights reserved.
REM ---------------------------------------------------------------------------


rmdir /Q /S target .apt_generated .myeclipse .externalToolBuilders 
del /Q /S .factorypath WebDiagram.gph .myumldata

call mvn clean checkstyle:checkstyle pmd:cpd findbugs:findbugs sonar:sonar install
call mvn clean -Dmaven.test.skip=true -Psonar sonar:sonar install
call mvn clean -DskipTests=true -Dmaven.test.failure.ignore=true sonar:sonar install


rmdir /Q /S .apt_generated .myeclipse .externalToolBuilders 
del /Q /S .factorypath WebDiagram.gph .myumldata
cd ..


pause