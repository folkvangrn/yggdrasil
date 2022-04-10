package ovh.nixenos.tab.server.users;

import ovh.nixenos.tab.server.users.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ovh.nixenos.tab.server.exceptions.*;
import ovh.nixenos.tab.server.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepo;

    CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDetails loadUserByUsername(String username) {
        return new CustomUserPrincipal(userRepo.findByUsername(username));
    }
}