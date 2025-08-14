package com.gymcrm.backend.model;

import com.gymcrm.backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Existing fields
    private String fullname;

    @Column(unique = true)
    private String email;

    private String contactNo;
    private String gender;
    private String membership;
    private String preferredTime;

    // Authentication fields
    private String password;
    private String otp;
    private boolean verified;
    private boolean active;
    private String createdAt;

    // New fields for authentication
    @Enumerated(EnumType.STRING)
    private Role role;

    // UserDetails implementation methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active; // Use your existing 'active' field
    }

    // Helper methods
    public boolean isVerified() {
        return verified;
    }

    public void activateAccount() {
        this.active = true;
        this.verified = true;
    }

    public void deactivateAccount() {
        this.active = false;
    }

//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Attendance> attendances;
}