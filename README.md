# tawk-test
A demo project for tawk recruitment team.

# Description

This application is written in java 11 and using spring framework.

The app will initially load data from provided source url into spring built-in cache, then it will automatically fetch from the remote source and update into cache to catch up with the most recent changes from source.

There is an Endpoint that can be used to filter the chat history by date.

EP: api/getChatInfo
Method: GET
Params:?from=yyyy-MM-dd&to=yyyy-MM-dd

# Test

Run all the tests with command: **mvn test** or **mvn install**

# Build 

Execute command: **mvn clean package spring-boot:repackage**

# Local run

In target folder after successfully built, execute command: **java -jar interview-test-1.0-SNAPSHOT.jar**
