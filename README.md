1. Create Spring boot project, 
2. Add few dependecies: 
- SpringWeb, 
- Spring Securty, 
- Lombok, 
- Spring Boot DevTools, 
- OAuth2 Resource Server 
- Spring Data MongoDB

3. Create basic Controller ( testing purpose )
4. Create config/SecurityConfig ( to enable testing enpoints )
-- issue: no decoder -- 
-- solution: add external auth provider-
5. use keyclock;
docker run -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:latest start-dev
6. add users in keyclock;
7. include it in application properties.
8. setup .env and .gitignore
9. link .env by clicking `run` -> `edit configuration` -> select file ( .env might be hidden, click the small eye icons to make it visible)
10. setup git, push etc.
	
