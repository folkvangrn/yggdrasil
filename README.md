# yggdrasil
Yggdrasil is a backend service for workshop-managing web application.

## How to run (current solution using docker-compose and nginx proxy)

 - checkout current repo
 - cd into docker/ directory: ```cd docker```
 - run ```docker-compose up``` 
 - enjoy your backend at ```http://localhost:8000```

## Changing the default settings

User can change connection string for the database, database user name and password, default admin's username, password, name, surname and hibernate's update strategy.
All of that is configurable via ```docker-compose.yml``` file.

## Manual deployment

JAR file will be provided to perform manual deployment. To tune settings one would need to set proper environment variables when executing on UNIX-based systems. Below are some sensible defaults that could be used:

  - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/tabprojekt
  - SPRING_DATASOURCE_USERNAME=tabprojekt
  - SPRING_DATASOURCE_PASSWORD=tabprojekt
  - SPRING_JPA_HIBERNATE_DDL_AUTO=update
  - APP_ADMIN_USERNAME=administrator
  - APP_ADMIN_PASSWORD=admin1234
  - APP_ADMIN_FIRSTNAME=test
  - APP_ADMIN_LASTNAME=test

## Backend API documentation
Swagger is avaible after running application under url: http://localhost:8080/swagger-ui/index.html