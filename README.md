
# Springboot Inventory 


WMS using **Keycloack**(AuthServer in local container), **Java**(Intellij) and **MongoDB**. Focuses on creating/monitoring product and its movement across warehouses, zones, bin and more.


### Status
The project is closed.
Those APIs are available  in Json file within this repository (***WMSSpringBoot.json***) and postman environment variables in  (***WMSSpringBootEnvironment.txt***)

### Project Conclusion.
Should have used SQL considering that WMS is highly relational dependend and let keyloack handle the entire user attributes.

## Deployment

1. Clone the master branch 

2. Install keycloack into local environment, it can be done via docker.

```bash
  docker run -d -p 8080:8080 \
  --name keycloak \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:latest start-dev
```

3. Add A user in Keycloack via admin page.

4. Setup MongoDB connection, one way is to create mongo connection instance is via MongoDb Atlas. 

5. Fill in .environment

6. Edit IntelliJ Configuration
```
Run -> Edit Configuration -> select .env file
```

