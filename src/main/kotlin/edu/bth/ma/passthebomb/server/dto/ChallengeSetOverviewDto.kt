package edu.bth.ma.passthebomb.server.dto

data class ChallengeSetOverviewDto(
    var id: String?,
    var creatorId: String? = null,
    var name: String? = null,
    val createdDate: Long? = null,
    var modifiedDate: Long? = null,
    var addedDate: Long? = null,
    var uploadedDate: Long? = null,
    var downloads: Int? = null,
)