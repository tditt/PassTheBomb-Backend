package edu.bth.ma.passthebomb.server.service

import edu.bth.ma.passthebomb.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// unused currently

@Service("customUserDetailsService")
@Transactional
class CustomUserDetailsService @Autowired constructor(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(name: String): UserDetails {
        val user: edu.bth.ma.passthebomb.server.domain.User = userRepository.findByName(name)
            ?: throw UsernameNotFoundException("User name not found: $name")
        return org.springframework.security.core.userdetails.User(
            user.name,
            user.hashedPassword,
            true,
            true,
            true,
            true,
            emptyList()
        )
    }
}