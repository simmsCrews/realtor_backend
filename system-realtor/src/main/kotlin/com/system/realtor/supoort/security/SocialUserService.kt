package com.system.realtor.supoort.security

import com.system.realtor.domain.user.User
import com.system.realtor.domain.user.UserRole
import com.system.realtor.domain.user.UserSocialAccount
import com.system.realtor.domain.user.UserSocialAccountRepository
import com.system.realtor.domain.user.UserStatus
import com.system.realtor.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
                role = UserRole.REALTOR,
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
