package edu.bth.ma.passthebomb.server.dto


data class ChallengeDto(
    val id: String? = null,
    val challengeSetId: String? = null,
    val createdDate: Long? = null,
    val text: String? = null,
    val timeLimit: Int? = null
)