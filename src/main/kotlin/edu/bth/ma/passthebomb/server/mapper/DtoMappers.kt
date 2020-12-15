package edu.bth.ma.passthebomb.server.mapper

import edu.bth.ma.passthebomb.server.domain.Challenge
import edu.bth.ma.passthebomb.server.domain.ChallengeSet
import edu.bth.ma.passthebomb.server.dto.ChallengeDto
import edu.bth.ma.passthebomb.server.dto.ChallengeSetDto
import edu.bth.ma.passthebomb.server.dto.ChallengeSetOverviewDto
import java.util.*


class DtoMappers {
    companion object {
        fun ChallengeSet.toDto(): ChallengeSetDto {
            return ChallengeSetDto(
                this.toChallengeSetOverviewDto(),
                challenges.map { c -> c.toDto(id!!) } as MutableList<ChallengeDto>
            )
        }

        fun ChallengeSet.toChallengeSetOverviewDto() = ChallengeSetOverviewDto(
            id = id,
            creatorId = creatorId,
            name = name,
            createdDate = dateToTimestamp(createdDate),
            modifiedDate = dateToTimestamp(modifiedDate),
            downloads = downloads
        )

        fun Challenge.toDto(challengeSetId: String) = ChallengeDto(
            id = id,
            challengeSetId = challengeSetId,
            createdDate = dateToTimestamp(createdDate),
            text = text,
            timeLimit = timeLimit
        )

        fun ChallengeSet.updateFromDto(challengeSetDto: ChallengeSetDto): ChallengeSet {
            name = challengeSetDto.challengeSetOverview.name
            modifiedDate = timeStampToDate(challengeSetDto.challengeSetOverview.modifiedDate)
            uploadedDate = Date(System.currentTimeMillis())
            // have to modify the list instead of assigning a new one, otherwise the db gives errors
            challenges.clear()
            challengeSetDto.challenges.forEach { c -> challenges.add(c.toChallenge()) }
            return this
        }


        fun ChallengeSetDto.toChallengeSet() = ChallengeSet(
            id = challengeSetOverview.id,
            creatorId = challengeSetOverview.creatorId,
            name = challengeSetOverview.name,
            createdDate = timeStampToDate(challengeSetOverview.createdDate),
            modifiedDate = timeStampToDate(challengeSetOverview.modifiedDate),
            uploadedDate = Date(System.currentTimeMillis()),
            downloads = challengeSetOverview.downloads,
            challenges = challenges.map { c -> c.toChallenge() } as MutableList<Challenge>
        )

        fun ChallengeDto.toChallenge() = Challenge(
            id = id,
            createdDate = timeStampToDate(createdDate),
            text = text,
            timeLimit = timeLimit
        )

        fun timeStampToDate(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}