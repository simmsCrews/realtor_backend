package com.system.admin.support.security

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class SecurityConfig(
    @Value("\${app.security.jwt.secret:change-me-please-change-me-please}") private val jwtSecret: String,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    ): SecurityFilterChain {
        return http
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/swagger-ui/**", "/v3/api-docs/**",
                        "/login/**", "/oauth2/**",
                        "/api/auth/refresh",
                    ).permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2Login { oauth2 ->
                oauth2.successHandler(oAuth2LoginSuccessHandler)
            }
            .oauth2ResourceServer { rs -> rs.jwt(Customizer.withDefaults()) }
            .build()
    }

    @Bean
    fun jwtSecretKey(): SecretKey {
        val bytes = jwtSecret.toByteArray(Charsets.UTF_8)
        return SecretKeySpec(bytes, "HmacSHA256")
    }

    @Bean
    fun jwtEncoder(secretKey: SecretKey): JwtEncoder =
        NimbusJwtEncoder(ImmutableSecret<SecurityContext>(secretKey))

    @Bean
    fun jwtDecoder(secretKey: SecretKey): JwtDecoder =
        NimbusJwtDecoder.withSecretKey(secretKey).build()
}