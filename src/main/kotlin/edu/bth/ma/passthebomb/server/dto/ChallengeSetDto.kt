package edu.bth.ma.passthebomb.server.dto


data class ChallengeSetDto(
    var challengeSetOverview: ChallengeSetOverviewDto,
    var challenges: MutableList<ChallengeDto>


)
