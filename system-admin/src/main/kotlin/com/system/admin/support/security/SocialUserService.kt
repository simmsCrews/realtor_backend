package com.system.admin.support.security

import com.system.admin.domain.User
import com.system.admin.domain.UserRole
import com.system.admin.domain.UserSocialAccount
import com.system.admin.domain.UserSocialAccountRepository
import com.system.admin.domain.UserStatus
import com.system.admin.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 소셜 로그인 시 (provider, providerSubject)로 우리 회원을 찾거나, 없으면 회원가입 후 반환.
 */
@Service
class SocialUserService(
    private val userRepository: UserRepository,
    private val userSocialAccountRepository: UserSocialAccountRepository,
) {

    @Transactional
    fun findOrCreateUser(
        provider: String,
        providerSubject: String,
        email: String?,
        name: String?,
    ): User {
        val existing = userSocialAccountRepository.findByProviderAndProviderSubject(provider, providerSubject)
        if (existing != null) {
            return existing.user
        }
        val user = userRepository.save(
            User(
                email = email,
                name = name,
                status = UserStatus.ACTIVE,
                role = UserRole.USER,
            ),
        )
        userSocialAccountRepository.save(
            UserSocialAccount(
                user = user,
                provider = provider,
                providerSubject = providerSubject,
            ),
        )
        return user
    }
}
