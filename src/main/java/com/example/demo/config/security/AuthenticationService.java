package com.example.demo.config.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));
        return new UserInfo(user.getUsername(), user.getPassword(), user.isEnabled(), Role.valueOf(user.getRole()));
    }

    @AllArgsConstructor
    class UserInfo implements UserDetails {

        private String username;
        private String password;
        private boolean enabled;
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(role);
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
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
            return this.enabled;
        }
    }

    enum Role implements GrantedAuthority {
        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN");

        private String name;

        Role(String name) {
            this.name = name;
        }

        @Override
        public String getAuthority() {
            return name;
        }
    }
}
