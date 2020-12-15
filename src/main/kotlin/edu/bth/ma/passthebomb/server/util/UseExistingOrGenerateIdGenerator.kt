package edu.bth.ma.passthebomb.server.util

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.UUIDGenerator
import org.springframework.context.annotation.ComponentScan
import java.io.Serializable
import java.util.*

@ComponentScan
class UseExistingIdOtherwiseGenerateUUID : UUIDGenerator() {
    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor, `object`: Any): Serializable {
        val id = session.getEntityPersister(null, `object`).classMetadata.getIdentifier(`object`, session)
        return id?.toString() ?: UUID.randomUUID().toString()
    }
}