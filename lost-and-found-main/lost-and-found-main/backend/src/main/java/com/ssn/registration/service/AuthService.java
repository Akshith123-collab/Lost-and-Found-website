package com.ssn.registration.service;

import com.ssn.registration.model.User;
import com.ssn.registration.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String email, String password, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        String normalizedRole = (role == null || role.isBlank()) ? "USER" : role.toUpperCase();
        Set<String> roles = Set.of(normalizedRole.equals("ADMIN") ? "ADMIN" : "USER");
        User user = new User(username, email, passwordEncoder.encode(password), roles);
        return userRepository.save(user);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    // Load user details used by Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ssn.registration.model.User appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Convert Set<String> roles into String[] roles for builder
        String[] roles = appUser.getRoles().stream().map(r -> r.replace("ROLE_", "")).toArray(String[]::new);

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(appUser.getUsername());
        builder.password(appUser.getPassword());
        builder.roles(roles);
        return builder.build();
    }
}
