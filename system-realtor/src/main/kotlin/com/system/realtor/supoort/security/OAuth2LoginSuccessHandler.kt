package com.system.realtor.supoort.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val jwtTokenService: JwtTokenService,
    private val socialUserService: SocialUserService,
    private val objectMapper: ObjectMapper,
    @Value("\${app.security.jwt.refresh-token-validity-seconds:604800}") private val refreshTokenMaxAgeSeconds: Int,
) : AuthenticationSuccessHandler {

    companion object {
        const val REFRESH_TOKEN_COOKIE_NAME = "refreshToken"
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val token = authentication as OAuth2AuthenticationToken
        val provider = token.authorizedClientRegistrationId
        val principal = token.principal

        val (providerSubject, email, name) = extractIdentity(provider, principal)

        val user = socialUserService.findOrCreateUser(
            provider = provider,
            providerSubject = providerSubject,
            email = email,
            name = name,
        )

        val userId = user.id.toString()
        val roles = user.roleNames()

        val accessToken = jwtTokenService.createAccessToken(
            userId = userId,
            roles = roles,
            email = user.email,
            name = user.name,
        )
        val refreshToken = jwtTokenService.createRefreshToken(
            userId = userId,
            roles = roles,
            email = user.email,
            name = user.name,
        )

        addRefreshTokenCookie(response, refreshToken)

        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()
        response.writer.use { w ->
            w.write(
                objectMapper.writeValueAsString(
                    mapOf(
                        "tokenType" to "Bearer",
                        "accessToken" to accessToken,
                        "userId" to user.id,
                        "provider" to provider,
                    ),
                ),
            )
        }
    }

    private fun extractIdentity(provider: String, principal: OAuth2User): Triple<String, String?, String?> {
        val email = principal.attributes["email"] as? String
        val name = principal.attributes["name"] as? String

        if (principal is OidcUser) {
            val sub = principal.subject
            val claims = principal.claims
            val oidcEmail = claims["email"] as? String
            val oidcName = (claims["name"] as? String)
                ?: (claims["given_name"] as? String)
            return Triple(sub, email ?: oidcEmail, name ?: oidcName)
        }

        val idCandidates = listOf("sub", "id", "response.id", "user.id", "uid")
        val providerSubject = idCandidates
            .firstNotNullOfOrNull { key -> principal.getNestedString(key) }
            ?: principal.name

        return Triple(providerSubject, email, name)
    }

    private fun OAuth2User.getNestedString(path: String): String? {
        val keys = path.split(".")
        var cur: Any? = this.attributes
        for (k in keys) {
            cur = (cur as? Map<*, *>)?.get(k)
        }
        return cur as? String
    }

    private fun addRefreshTokenCookie(response: HttpServletResponse, refreshToken: String) {
        val cookie = Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken).apply {
            isHttpOnly = true
            secure = false
            maxAge = refreshTokenMaxAgeSeconds
            path = "/"
            setAttribute("SameSite", "Lax")
        }
        response.addCookie(cookie)
    }
}

