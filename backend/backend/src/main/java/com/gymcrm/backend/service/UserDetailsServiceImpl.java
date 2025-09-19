package com.gymcrm.backend.service;

import com.gymcrm.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//added by swarup
import com.gymcrm.backend.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    // Try case-insensitive email lookup (common in your codebase)
    User user = userRepository.findByEmailIgnoreCase(usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Map to Spring Security UserDetails
    boolean enabled = user.isActive(); // or user.isVerified() if you require verification
    return new org.springframework.security.core.userdetails.User(
            user.getEmail(), // username == email (must match subject in JWT)
            user.getPassword(),
            enabled,         // enabled
            true,            // accountNonExpired
            true,            // credentialsNonExpired
            true,            // accountNonLocked
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
    );
}
}