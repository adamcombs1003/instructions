### Trade Instructions App
## This project requires Docker installed locally.
## Spring boot run command will start Kafka and Zookeeper Docker containers and run application locally.

## mvnw spring-boot:run

### Files
## The following files are located at the root of the project
- csv-file.csv (sample csv file to upload)
- json-file.json (sample json file to upload)
- docker-compose.yml
- pom.yml

### Swagger UI
## Swagger UI can be used to upload either csv or json files to the service.
- Swagger UI url - http://localhost:8080/swagger-ui/index.html

### Kafka
- Messages are published to Kafka when API is called with valid json.
- Kafka Listener will listen for and log published messages.
