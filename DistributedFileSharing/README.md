# DistributedFileSharing [![Build Status](https://travis-ci.org/arunans23/DistributedFileSharing.svg?branch=master)](https://travis-ci.org/arunans23/DistributedFileSharing)

A distributed file sharing system which has an overlay structure of gnutella

# To Run :

Run the project in IntelIJ.
The main class is src/main/java/com/semicolon/ds/gui/Main

# To create a jar and run

1. In the root folder, run `mvn clean package`
2. Jar will be formed in the targer folder. You can simply run it by double clicking, or running it in a terminal with the following command.
`java -jar <filename>`

# Note
You have to start the bootstrap server before running the program. Set the bootstrap properties in the `Bootstrap.properties` file.
