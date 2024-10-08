package com.schoolportal.config;

import com.schoolportal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/teacher/**").hasRole("TEACHER")
                                .requestMatchers("/student/**").hasRole("STUDENT")
                                .requestMatchers("/parent/**").hasRole("PARENT")
                                .requestMatchers("/", "/index", "/login", "/css/**", "/js/**","/img/**").permitAll() // Permit access to these paths
                                // Chỉ cho phép admin thực hiện các hành động CRUD với bài viết
                                .requestMatchers("/news/create", "/news/edit/**", "/news/delete/**").hasRole("ADMIN")
                                // Các quyền khác chỉ có thể xem bài viết
                                .requestMatchers("/news/view/**").authenticated()
                                .requestMatchers("/subject/**").permitAll()
                                .anyRequest().authenticated() // Bảo mật tất cả các yêu cầu còn lại
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Set the login page
                                .permitAll() // Allow all to access the login page
                                .defaultSuccessUrl("/default", true) // Redirect to default URL after login
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Logout request URL
                                .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                                .permitAll() // Allow all to access logout
                )
                .csrf(csrf ->
                        csrf
                                .ignoringRequestMatchers("/logout") // Ignore CSRF for logout URL
                )
                .userDetailsService(userDetailsService); // Set the user details service

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
