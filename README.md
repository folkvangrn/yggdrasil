# yggdrasil
Yggdrasil is a backend service for workshop-managing web application.

## How to run (current solution using Docker compose and nginx proxy)

 - checkout current repo
 - cd into docker/ directory: ```cd docker```
 - run ```docker-compose up``` 
 - enjoy your backend at ```http://localhost:8000```

## How to run (deprcated)

 - cd into server/ directory: ```cd server```
 - build new jar file: ```./mvnw clean package``` (for UNIX-based systems)
 - for windows hosts: ```./mvnw.exe clean package```
 - run built jar file: ```java -jar target/server-0.0.1-SNAPSHOT.jar```

For development purposes postgresql database is needed to be accessible on host system. Configure it as follows:

 - add new database tabprojekt
 - add new user with full access to that database, with password tabprojekt and username tabprojekt
 - make sure postgres is running on port 5432
 - all above things can be configured via application.properties file 

Swagger is avaible after running application under url: http://localhost:8080/swagger-ui/index.html
