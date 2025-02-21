package com.example.User.configuration;

import com.example.User.constant.APIRequestURL;
import com.example.User.database.RedisUserSecretDao;
import com.example.User.database.UserAuthTokenQueryDao;
import com.example.User.filter.CustomAuthorizationFilterSpring;
import com.example.User.helper.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static com.example.User.constant.APIRequestURL.DIGIPAY_AUTH_DOCKER_API_URL;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    private final RedisUserSecretDao redisUserSecretDao;
    private final UserUtility authUtility;

    private  final UserAuthTokenQueryDao userAuthTokenQueryDao;
    @Autowired
    SpringSecurityConfiguration(RedisUserSecretDao redisUserSecretDao,
                                UserUtility authUtility, UserAuthTokenQueryDao userAuthTokenQueryDao) {
        this.authUtility = authUtility;
        this.redisUserSecretDao = redisUserSecretDao;
        this.userAuthTokenQueryDao = userAuthTokenQueryDao;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOriginPatterns(List.of("*"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
                return configuration;
            });
        });
        http.headers(headers ->
                headers.xssProtection(
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        cps -> cps.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval';")
                )
        );
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER));
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(DIGIPAY_AUTH_DOCKER_API_URL + "**").authenticated());
        http.addFilterBefore(new CustomAuthorizationFilterSpring(redisUserSecretDao, authUtility, userAuthTokenQueryDao), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /* Will be Use to By Pass Spring Security For GET API */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(HttpMethod.GET, APIRequestURL.getByPassApiEndpoints);
            web.ignoring().requestMatchers(HttpMethod.POST, APIRequestURL.postByPassApiEndpoints);
            web.ignoring().requestMatchers(HttpMethod.PUT, APIRequestURL.putByPassApiEndpoints);
            web.ignoring().requestMatchers(HttpMethod.DELETE, APIRequestURL.deleteByPassApiEndpoints);
        };
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}