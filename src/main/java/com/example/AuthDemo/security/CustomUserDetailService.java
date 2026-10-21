package com.example.AuthDemo.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.AuthDemo.entity.User;
import com.example.AuthDemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository repository;

    @Override 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
    }
}
