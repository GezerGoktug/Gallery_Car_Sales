package com.goktug.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.goktug.exceptions.AuthEntryPoint;
import com.goktug.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String REGISTER = "/rest/api/auth/register";
    private static final String REFRESH_TOKEN = "/rest/api/auth/refreshToken";
    private static final String AUTHENTICATE = "/rest/api/auth/authenticate";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    //! daha önce jwt authentication filter da tanımladığımız her istekte jwt yi kontrol edecek metodu burda hangi
    //! istekler için olacağını ayarlıyoruz.Session ayarlama
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
        http.csrf().disable()
                .authorizeRequests(request -> request.requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN).permitAll()
                        .anyRequest().authenticated())
                 .exceptionHandling()
                 .authenticationEntryPoint(authEntryPoint).and()       
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
