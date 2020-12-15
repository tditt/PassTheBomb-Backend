package edu.bth.ma.passthebomb.server.domain

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*


@Entity
data class ChallengeSet(
    @Id
    @GenericGenerator(
        name = "UseExistingIdOtherwiseGenerateUUID",
        strategy = "edu.bth.ma.passthebomb.server.util.UseExistingIdOtherwiseGenerateUUID"
    )
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUUID")
    var id: String? = null,
    var creatorId: String? = null,
    @Column(unique = true)
    var name: String? = null,
    val createdDate: Date? = null,
    var modifiedDate: Date? = null,
    var uploadedDate: Date? = null,
    var downloads: Int? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    var challenges: MutableList<Challenge> = mutableListOf()
)