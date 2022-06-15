# yggdrasil
Yggdrasil is a backend service for workshop-managing web application.

## How to run (current solution using docker-compose and nginx proxy)

 - checkout current repo: ```git clone https://github.com/folkvangrn/yggdrasil.git```
 - cd into docker/ directory: ```cd docker```
 - run ```docker-compose up``` 
 - enjoy your backend at ```http://localhost:8000```

## Changing the default settings

User can change connection string for the database, database user name and password, default admin's username, password, name, surname and hibernate's update strategy.
All of that is configurable via ```docker-compose.yml``` file. Note that ```nginx.conf``` file is vital to operation of this configuration, as it contains settings for
nginx proxy that is used to bypass spring library limitations when it comes to CORS handling.

Please refer to ```docker-compose.yml``` file for more details.

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

After that simple ```java -jar app_name.jar``` should set up a backend server. Configuring proxy to serve endpoints at different URLs is highly encouraged, as it might be used to perform load balancing and version management.

## Backend API documentation
Swagger is avaible after running application on endpoint: ```/swagger-ui/index.html```