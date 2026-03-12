package com.system.admin.support.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtTokenService(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    @Value("\${app.security.jwt.issuer:realtor-backend}") private val issuer: String,
    @Value("\${app.security.jwt.access-token-validity-seconds:3600}") private val accessTokenValiditySeconds: Long,
    @Value("\${app.security.jwt.refresh-token-validity-seconds:604800}") private val refreshTokenValiditySeconds: Long,
) {
    /**
     * 우리 서비스 회원 기준으로 access token 발급. (userId = sub)
     */
    fun createAccessToken(
        userId: String,
        roles: List<String>,
        email: String?,
        name: String?,
    ): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(accessTokenValiditySeconds))
            .subject(userId)
            .claim("email", email)
            .claim("name", name)
            .claim("roles", roles)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    /**
     * 우리 서비스 회원 기준으로 refresh token 발급.
     */
    fun createRefreshToken(
        userId: String,
        roles: List<String>,
        email: String?,
        name: String?,
    ): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(refreshTokenValiditySeconds))
            .subject(userId)
            .claim("type", "refresh")
            .claim("email", email)
            .claim("name", name)
            .claim("roles", roles)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    /**
     * 쿠키의 refresh token으로 새 access token 발급. 유효하지 않으면 null.
     */
    fun refreshAccessToken(refreshToken: String?): String? {
        if (refreshToken.isNullOrBlank()) return null
        return try {
            val jwt: Jwt = jwtDecoder.decode(refreshToken)
            if (jwt.getClaimAsString("type") != "refresh") return null
            val userId = jwt.subject
            createAccessToken(
                userId = userId,
                roles = (jwt.getClaim("roles") as? List<*>)?.map { it.toString() } ?: emptyList(),
                email = jwt.getClaimAsString("email"),
                name = jwt.getClaimAsString("name"),
            )
        } catch (_: Exception) {
            null
        }
    }
}

