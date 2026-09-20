package com.movie.api.config;

import com.movie.api.auth.AuthorizationFilter;
import com.movie.api.model.support.ResponseResult;
import com.movie.api.utils.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security configuration
 * Enable method security annotations explicitly
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP security configuration
     *
     * @param http h
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Enable CORS
        http.csrf().disable().cors();

        //Allow pages to be embedded in iframes
        http.headers().frameOptions().disable();

        //Disable server-side sessions
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Authentication failed
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            ResponseUtil.writeJson(response, new ResponseResult<>(403, "Authentication failed. Please log in again"));
        });

        http.addFilter(new AuthorizationFilter(authenticationManagerBean()));

    }

    /**
     * Allow the Authorization header in cross-origin requests.
     * Configure CORS for frontend API requests.
     *
     * @return *
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedHeader("DELETE");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
