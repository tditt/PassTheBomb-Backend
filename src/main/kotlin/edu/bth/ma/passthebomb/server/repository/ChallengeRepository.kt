package edu.bth.ma.passthebomb.server.repository

import edu.bth.ma.passthebomb.server.domain.Challenge
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
interface ChallengeRepository : CrudRepository<Challenge?, String?>