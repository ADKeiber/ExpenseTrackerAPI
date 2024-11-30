package com.adk.expensetracker.service;

import com.adk.expensetracker.model.Role;
import com.adk.expensetracker.model.User;
import com.adk.expensetracker.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User service used to interact with UserDetails and security
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    /**
     * Loads a user by its username
     * @param username the username identifying the user whose data is required.
     * @return {@link UserDetails} containing user information
     * @throws UsernameNotFoundException if username isn't found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Maps a list of roles into a collection of granted authorities
     * @param roles {@link List} of {@link Role} the roles needed to be mapped
     * @return {@link Collection} of {@link GrantedAuthority} of the mapped roles
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getValue())).collect(Collectors.toList());
    }
}
