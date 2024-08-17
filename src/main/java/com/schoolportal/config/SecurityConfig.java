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
                                .anyRequest().authenticated() // Bảo mật tất cả các yêu cầu còn lại
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Đặt trang đăng nhập
                                .permitAll() // Cho phép tất cả truy cập trang đăng nhập
                                .defaultSuccessUrl("/default", true) // Chuyển hướng sau khi đăng nhập thành công
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Đường dẫn yêu cầu logout
                                .logoutSuccessUrl("/login?logout") // Chuyển hướng sau khi đăng xuất thành công
                                .permitAll() // Cho phép tất cả truy cập logout
                )
                .csrf(csrf ->
                        csrf
                                .ignoringRequestMatchers("/logout") // Cấu hình bỏ qua CSRF cho đường dẫn logout
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
