package edu.bth.ma.passthebomb.server.controller

import edu.bth.ma.passthebomb.server.dto.ChallengeSetDto
import edu.bth.ma.passthebomb.server.dto.ChallengeSetOverviewDto
import edu.bth.ma.passthebomb.server.dto.GenericResponseDto
import edu.bth.ma.passthebomb.server.exception.RestParameterException
import edu.bth.ma.passthebomb.server.service.ChallengeSetService
import edu.bth.ma.passthebomb.server.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/api/"])
class ChallengeSetController constructor(
    private val challengeSetService: ChallengeSetService,
    private val userService: UserService
) {


    val logger = LoggerFactory.getLogger(ChallengeSetController::class.java)

    @PostMapping(value = ["/upload"])
    fun createOrUpdate(
        @RequestBody @Valid challengeSetDto: ChallengeSetDto?,
        principal: Principal?
    ): GenericResponseDto {
        challengeSetService.createOrUpdateChallengeSet(challengeSetDto!!)
        logger.info("Upload received: $challengeSetDto")
        return GenericResponseDto("OK")
    }

    @GetMapping(value = ["/download"])
    fun download(@RequestParam globalId: String?): ChallengeSetDto? {
        val challengeSetDto = challengeSetService.downloadChallengeSet(globalId)
        logger.info("Get request received: $challengeSetDto")
        return challengeSetDto
    }

    @GetMapping(value = ["/downloadOverviews"])
    fun downloadOverviews(): List<ChallengeSetOverviewDto> {
        val challengeOverviews = challengeSetService.downloadChallengeSetOverviews()
        logger.info("Get all request received: $challengeOverviews")
        return challengeOverviews
    }

    @GetMapping(value = ["/getNumberOfNewSets"])
    fun getNumberOfNewSets(@RequestParam userIdAndLastDownloadDate: Map<String, String>?): GenericResponseDto {
        if (userIdAndLastDownloadDate == null || userIdAndLastDownloadDate.isEmpty() || userIdAndLastDownloadDate.entries.size != 2) throw RestParameterException()
        val userId = userIdAndLastDownloadDate["userId"]
        val lastDownloadLong: Long?
        try {
            lastDownloadLong = userIdAndLastDownloadDate["lastDownloadDate"]?.toLong()
        } catch (e: NumberFormatException) {
            throw RestParameterException()
        }
        if (userId == null || lastDownloadLong == null) throw RestParameterException()
        val lastDownloadDate = Date(lastDownloadLong)
        logger.info("Get request received: getNumberOfNewSets( UserID: $userId, Last download date: $lastDownloadDate) ")
        return GenericResponseDto(challengeSetService.getNumberOfNewChallengeSets(userId, lastDownloadDate))
    }

    @DeleteMapping(value = ["/delete"])
    fun delete(@RequestParam globalId: String?): GenericResponseDto {
        logger.info("Delete request received: $globalId")
        challengeSetService.deleteChallengeSet(globalId!!)
        return GenericResponseDto("OK")
    }

    @DeleteMapping(value = ["/deleteAll"])
    fun deleteAll(): GenericResponseDto {
        logger.info("Delete all request received")
        challengeSetService.deleteAllChallengeSets()
        return GenericResponseDto("OK")
    }

}