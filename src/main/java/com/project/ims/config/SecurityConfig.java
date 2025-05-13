// com.project.ims.config.SecurityConfig.java
package com.project.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Assuming you are still using NoOpPasswordEncoder for now
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.project.ims.Service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

// Assuming CustomUserDetailsService exists in com.project.ims.config
// import com.project.ims.config.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // You need to provide your actual CustomUserDetailsService implementation here
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // WARNING: NoOpPasswordEncoder is INSECURE and should only be used for development/testing.
        // In production, use BCryptPasswordEncoder or another strong hashing algorithm.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            private final SimpleUrlAuthenticationSuccessHandler userSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/user/products");
            private final SimpleUrlAuthenticationSuccessHandler adminSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/admin/dashboard");
            private final SimpleUrlAuthenticationSuccessHandler defaultSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/");

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                 Authentication authentication) throws IOException, jakarta.servlet.ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                if (roles.contains("ROLE_ADMIN")) { // Check for ROLE_ADMIN
                    this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                } else if (roles.contains("ROLE_USER")) { // Check for ROLE_USER
                    this.userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                } else {
                    this.defaultSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                }
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // Allow authenticated access to the endpoint that provides the dashboard URL
                .requestMatchers("/user/get-dashboard-url").authenticated()
                // Restrict admin paths to users with the ROLE_ADMIN authority
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Restrict user paths to users with the ROLE_USER authority
                .requestMatchers("/user/**").hasRole("USER")
                // Allow public access to these paths
                .requestMatchers("/", "/home", "/login","/register", "/error", "/Css/**", "/JS/**", "/images/**").permitAll()
                // Any other request must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }
}