package com.system.realtor.adapter.`in`

import com.system.realtor.supoort.security.JwtTokenService
import com.system.realtor.supoort.security.OAuth2LoginSuccessHandler
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtTokenService: JwtTokenService,
) {

    @PostMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<Map<String, Any>> {
        val refreshToken = request.cookies
            ?.firstOrNull { it.name == OAuth2LoginSuccessHandler.Companion.REFRESH_TOKEN_COOKIE_NAME }
            ?.value

        val accessToken = jwtTokenService.refreshAccessToken(refreshToken)
        if (accessToken == null) {
            clearRefreshTokenCookie(response)
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                .body(mapOf("error" to "invalid_or_expired_refresh_token"))
        }

        return ResponseEntity.ok(
            mapOf(
                "tokenType" to "Bearer",
                "accessToken" to accessToken,
            ),
        )
    }

    private fun clearRefreshTokenCookie(response: HttpServletResponse) {
        val cookie = Cookie(OAuth2LoginSuccessHandler.Companion.REFRESH_TOKEN_COOKIE_NAME, "").apply {
            isHttpOnly = true
            secure = false
            maxAge = 0
            path = "/"
            setAttribute("SameSite", "Lax")
        }
        response.addCookie(cookie)
    }
}