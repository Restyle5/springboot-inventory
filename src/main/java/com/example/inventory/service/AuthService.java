package com.example.inventory.service;

import com.example.inventory.dto.request.RegisterRequest;
import com.example.inventory.dto.response.RegisterResponse;
import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final Keycloak keycloak;
    private final UserRepository userRepository;

    @Value("${keycloak.admin.realm}")
    private String targetRealm;

    public RegisterResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email is already registered"
            );
        }

        // Create Keycloak instance
        UserRepresentation keycloakUser = getUserRepresentation(request);

        RealmResource realm = keycloak.realm(targetRealm);
        Response response = realm.users().create(keycloakUser);

        if (response.getStatus() == 409) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email is already registered in auth server"
            );
        }

        if (response.getStatus() != 201) {
            log.error("Keycloak user creation failed with status: {}", response.getStatus());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user"
            );
        }

        // Get Keycloak user ID from Location header
        String locationPath = response.getLocation().getPath();
        String keycloakId   = locationPath.substring(locationPath.lastIndexOf('/') + 1);

        // Save in MongoDb
        userRepository.save(
                User.builder()
                        .keycloakId(keycloakId)
                        .email(request.getEmail())
                        .name(request.getFirstName() + " " + request.getLastName())
                        .build()
        );

        return RegisterResponse.builder()
                .message("Registration successful. Please login.")
                .email(request.getEmail())
                .build();
    }

    private static @NonNull UserRepresentation getUserRepresentation(RegisterRequest request) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(request.getUsername());
        keycloakUser.setEnabled(true);
        keycloakUser.setEmail(request.getEmail());
        keycloakUser.setFirstName(request.getFirstName());
        keycloakUser.setLastName(request.getLastName());
        keycloakUser.setEmailVerified(true);            // set false if you want email verification
        keycloakUser.setCredentials(List.of(credential));
        return keycloakUser;
    }
}