package edu.bth.ma.passthebomb.server.service

import edu.bth.ma.passthebomb.server.domain.ChallengeSet
import edu.bth.ma.passthebomb.server.dto.ChallengeSetDto
import edu.bth.ma.passthebomb.server.dto.ChallengeSetOverviewDto
import edu.bth.ma.passthebomb.server.exception.ChallengeSetIdNotSetException
import edu.bth.ma.passthebomb.server.exception.ChallengeSetNotFoundException
import edu.bth.ma.passthebomb.server.mapper.DtoMappers.Companion.toChallengeSet
import edu.bth.ma.passthebomb.server.mapper.DtoMappers.Companion.toChallengeSetOverviewDto
import edu.bth.ma.passthebomb.server.mapper.DtoMappers.Companion.toDto
import edu.bth.ma.passthebomb.server.mapper.DtoMappers.Companion.updateFromDto
import edu.bth.ma.passthebomb.server.repository.ChallengeRepository
import edu.bth.ma.passthebomb.server.repository.ChallengeSetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChallengeSetService @Autowired constructor(
    private val challengeSetRepository: ChallengeSetRepository,
    private val challengeRepository: ChallengeRepository
) {

    fun downloadChallengeSetOverviews(): List<ChallengeSetOverviewDto> {
        return challengeSetRepository.findAllByOrderByDownloadsDesc().toList()
            .map { c -> c.toChallengeSetOverviewDto() }
    }

    @Transactional
    @Throws(ChallengeSetNotFoundException::class)
    fun downloadChallengeSet(id: String?): ChallengeSetDto? {
        if (id == null) throw ChallengeSetIdNotSetException()
        val challengeSet = challengeSetRepository.findById(id).orElseThrow { ChallengeSetNotFoundException() }
            ?: throw ChallengeSetNotFoundException()
        challengeSet.downloads = if (challengeSet.downloads == null) 1 else challengeSet.downloads!! + 1
        challengeSetRepository.save(challengeSet)
        return challengeSet.toDto()
    }

    @Transactional
    @Throws(ChallengeSetIdNotSetException::class)
    fun createOrUpdateChallengeSet(challengeSetDto: ChallengeSetDto) {
        val id = challengeSetDto.challengeSetOverview.id ?: throw ChallengeSetIdNotSetException()
        val challengeSetInDb = challengeSetRepository.findById(id)
        if (challengeSetInDb.isEmpty) {
            val challengeSetNew = challengeSetDto.toChallengeSet()
            challengeSetNew.uploadedDate = Date(System.currentTimeMillis())
            saveChallengeSet(challengeSetNew)
        } else {
            saveChallengeSet(challengeSetInDb.get().updateFromDto(challengeSetDto))
        }
    }

    @Transactional
    fun saveChallengeSet(challengeSet: ChallengeSet) {
        challengeSetRepository.save(challengeSet)
        challengeSet.challenges.forEach { c -> challengeRepository.save(c) }
    }

    @Transactional
    fun deleteChallengeSet(id: String) {
        challengeSetRepository.deleteById(id)
    }

    @Transactional
    fun deleteAllChallengeSets() {
        challengeSetRepository.deleteAllByCreatorIdNot("INITIAL")
    }

    fun getNumberOfNewChallengeSets(userId: String, lastDownloadDate: Date): Int {
        return challengeSetRepository.findAllByCreatorIdNotAndUploadedDateAfterOrderByUploadedDate(
            userId,
            lastDownloadDate
        ).size
    }

}