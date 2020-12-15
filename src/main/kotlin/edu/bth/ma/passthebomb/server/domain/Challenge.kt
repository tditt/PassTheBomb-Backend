package edu.bth.ma.passthebomb.server.domain

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class Challenge(
    @Id
    @GenericGenerator(
        name = "UseExistingIdOtherwiseGenerateUUID",
        strategy = "edu.bth.ma.passthebomb.server.util.UseExistingIdOtherwiseGenerateUUID"
    )
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUUID")
    val id: String? = null,
    val createdDate: Date? = null,
    val text: String? = null,
    val timeLimit: Int? = null
)