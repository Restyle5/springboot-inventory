package com.example.inventory.helper;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final UserRepository userRepository;

    /**
     * @return User instance.
     */
    public User getCurrentUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String keycloakId = jwt.getSubject();

        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    /**
     * @param createdBy Model: Tenant (createdBy), referring to Model: User (id).
     */
    public void checkOwnership(String createdBy){
        User currentUser = getCurrentUser();
        if(!createdBy.equals(currentUser.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant Not found.");
        }
    }
}