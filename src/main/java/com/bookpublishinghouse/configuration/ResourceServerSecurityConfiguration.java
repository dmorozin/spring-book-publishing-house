package com.bookpublishinghouse.configuration;

import com.bookpublishinghouse.exception.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
public class ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_SCOPE = "SCOPE_admin";
    private static final String AUTHOR_SCOPE = "SCOPE_author";

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(requests ->
                                       requests.antMatchers("/api/auth/author/**")
                                               .hasAuthority(ADMIN_SCOPE)
                                               .antMatchers(HttpMethod.POST, "/api/auth/book/**")
                                               .hasAuthority(AUTHOR_SCOPE)
                                               .antMatchers("/api/author/**").permitAll()
                                               .antMatchers("/api/book/**").permitAll())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        return new RestAccessDeniedHandler();
    }
}
