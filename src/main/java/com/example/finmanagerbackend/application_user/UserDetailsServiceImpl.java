package com.example.finmanagerbackend.application_user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for loading user details for authentication
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl( ApplicationUserRepository applicationUserRepository ) {
        this.applicationUserRepository = applicationUserRepository;
    }

    // Implementation of UserDetailsService method to load user details by username
    @Override
    @Transactional
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

        // Find the user by email (username) in the repository
        ApplicationUser user = applicationUserRepository.findByEmail( username )
                .orElseThrow( () -> new UsernameNotFoundException( String.format( "User with email %s not found", username ) ) );

        // Build and return UserDetailsImpl using the found user
        return UserDetailsImpl.build( user );
    }
}
