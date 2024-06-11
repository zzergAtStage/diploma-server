# Earthquake Monitoring Desktop Application

## Overview

The Earthquake Monitoring Desktop Application is a Java-based program designed to provide users with real-time data on seismic activities. This application allows users to log in, view earthquake data, and access detailed maps of affected areas. It ensures that authenticated users have full access to comprehensive earthquake information, while non-authenticated users can only view limited data.

## Features

- **User Authentication**: Secure login for registered users.
- **Real-time Data**: Fetch and display the latest earthquake data.
- **Map Visualization**: Detailed maps showing the location and intensity of earthquakes.
- **User Roles**: Differentiated access levels for authenticated and non-authenticated users.

## Technologies Used

- **Java**: Core programming language.
- **Swing**: For GUI development.
- **HTTPS**: Secure communication with the server.
- **Publisher-Subscriber Pattern**: For handling user events and notifications.

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/earthquake-monitoring-desktop.git
   ```


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

## 2run https server, execute [HttpsEnabledApplication.java](src%2Fmain%2Fjava%2Fcom%2Fzergatstageg%2Fs02cruddemo%2Fssl%2FHttpsEnabledApplication.java)
