
# Springboot Inventory 


WMS using **Keycloack**(AuthServer in local container), **Java**(Intellij) and **MongoDB**. Focuses on creating/monitoring product and its movement across warehouses, zones, bin and more.


### Status
The project is still under development. It has a couples more module to be included.
Those APIs are available  in Json file within this repository (***WMSSpringBoot.json***) and postman environment variables in  (***WMSSpringBootEnvironment.txt***)

    1. Tenant - pushed
    2. User - pushed
    3. Warehouse - pushed
    4. Zone- pushed
    5. Bin -pushed
    6. Product - pushed 
    7. StockUnit - NOT YET
    8. StockMovement - NOT YET






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

