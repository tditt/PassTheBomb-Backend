package edu.bth.ma.passthebomb.server.repository

import edu.bth.ma.passthebomb.server.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

//unused currently

@Repository
interface UserRepository : CrudRepository<User?, Int?> {
    fun findByName(name: String?): User?
}