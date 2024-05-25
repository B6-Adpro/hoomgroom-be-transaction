package hoomgroom.product.Auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                "/api/promo/delete/**",
                        "/api/promo/fixed/create",
                        "/api/promo/fixed/update/**",
                        "/api/promo/fixed/delete/**",
                        "/api/promo/percent/create",
                        "/api/promo/percent/update/**",
                        "/api/promo/percent/delete/**")
                    .hasAuthority("ADMIN")
                    .requestMatchers(
                        "/api/promo/",
                        "/api/promo/id/**",
                        "/api/promo/fixed/all",
                        "/api/promo/fixed/id/**",
                        "/api/promo/percent/all",
                        "/api/promo/percent/id/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
