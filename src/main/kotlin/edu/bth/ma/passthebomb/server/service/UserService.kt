package edu.bth.ma.passthebomb.server.service

import edu.bth.ma.passthebomb.server.domain.User
import edu.bth.ma.passthebomb.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

//unused currently

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {
    fun getUserByName(name: String?): User? {
        return userRepository.findByName(name)
            ?: throw UsernameNotFoundException("User name not found: $name")
    }
}