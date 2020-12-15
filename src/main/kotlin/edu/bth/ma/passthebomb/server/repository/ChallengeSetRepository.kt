package edu.bth.ma.passthebomb.server.repository

import edu.bth.ma.passthebomb.server.domain.ChallengeSet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChallengeSetRepository : CrudRepository<ChallengeSet?, String?> {

    fun findAllByOrderByDownloadsDesc(): List<ChallengeSet>

    fun findAllByCreatorIdNotAndUploadedDateAfterOrderByUploadedDate(
        creatorId: String,
        uploadedDate: Date
    ): List<ChallengeSet>

    fun deleteAllByCreatorIdNot(creatorId: String)

}