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