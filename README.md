## Trade Instructions App
### This project requires Docker installed locally.
### Spring boot run command will start Kafka and Zookeeper Docker containers and run application locally.

### mvnw spring-boot:run

## Files
### The following files are located at the root of the project
- csv-file.csv (sample csv file to upload)
- json-file.json (sample json file to upload)
- docker-compose.yml
- pom.yml

## Swagger UI
### Swagger UI can be used to upload either csv or json files to the service.
- Swagger UI url - http://localhost:8080/swagger-ui/index.html

## CURL
### The following curl can also be used to call API
curl --location 'http://localhost:8080/upload-file' --form 'file=@"{path-to-json-or-csv}"'

## Kafka
- Messages are published to Kafka when API is called with valid json or csv.
- Kafka Listener will listen for and log published messages.

## Notes
### In the interest of time, some requirements are not yet completed.  
### I will continue working on the following enhancements:
- Secure endpoint (Spring Security)
- Unit Tests (J-Unit & Mockito)
- Asyncronous Kafka publishing (Executor Service)
