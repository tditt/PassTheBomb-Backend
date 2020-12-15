package edu.bth.ma.passthebomb.server.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

// unused currently

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
    var name: String? = null
    var email: String? = null
    var hashedPassword: String? = null
}