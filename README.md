# Earthquake Monitoring Server

## Overview

The Earthquake Monitoring Server is a robust backend service that provides data storage, user authentication, and RESTful API endpoints for the Earthquake Monitoring Desktop Application. It is built with Spring Boot and designed to ensure secure and efficient handling of user data and earthquake information.

## Features

- **SSL Security**: All communications are secured with HTTPS.
- **User Authentication**: Basic authentication to manage user access.
- **RESTful API**: Endpoints for accessing earthquake data.
- **Admin Web Interface**: For managing users and their licenses.
- **Scheduled Data Parsing**: Regular updates from earthquake data feeds.

## Technologies Used

- **Spring Boot**: Framework for building the server application.
- **Spring Security**: For authentication and authorization.
- **JPA/Hibernate**: For database interactions.
- **H2 Database**: In-memory database for development and testing.
- **SLF4J/Logback**: For logging.

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/earthquake-monitoring-server.git
   ```
2. Navigate to the project directory, build and run the application:
   ```bash
   cd earthquake-monitoring-server
   ./mvnw spring-boot:run
   ```
## 2run https server, execute [HttpsEnabledApplication.java](src%2Fmain%2Fjava%2Fcom%2Fzergatstageg%2Fs02cruddemo%2Fssl%2FHttpsEnabledApplication.java)


### Generate server keys:
1. Generate server key store  
```shell
keytool -genkey -alias serverkey -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -keystore serverkeystore.p12 -storepass password -ext san=ip:127.0.0.1,dns:localhost
```  
2. Export key to server
```shell
keytool -exportcert -keystore serverkeystore.p12 -alias serverkey -storepass password -rfc -file server-certificate.pem
```
3. Add server key to client store
```shell
keytool -import -trustcacerts -file server-certificate.pem -keypass password -storepass password -keystore clienttruststore.jks
```

### Generate client keys
1. Create client store
```shell
keytool -genkey -alias clientkey -keyalg RSA -keysize 2048 -sigalg SHA256withRSA -keystore clientkeystore.p12 -storepass password -ext san=ip:127.0.0.1,dns:localhost

keytool -exportcert -keystore clientkeystore.p12 -alias clientkey -storepass password -rfc -file client-certificate.pem

keytool -import -trustcacerts -file client-certificate.pem -keypass password -storepass password -keystore servertruststore.jks
```
## API Endpoints
- GET /api/earthquakes - Retrieve earthquake data.
- POST /api/login - User login endpoint.
- Admin Console: Access the H2 console at /console (Admin role required).

## Requirements
- Java 8 or later: Ensure you have the latest version of Java installed.
- Maven: For building and running the project.
- Internet Connection: For fetching real-time earthquake data.
