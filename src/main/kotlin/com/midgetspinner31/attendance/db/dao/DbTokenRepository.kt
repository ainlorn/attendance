package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.PersistentLogin
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class DbTokenRepository(
    private val persistentLoginRepository: PersistentLoginRepository
) : PersistentTokenRepository {
    @Transactional
    override fun createNewToken(token: PersistentRememberMeToken) {
        if (persistentLoginRepository.existsBySeries(token.series))
            throw DataIntegrityViolationException("Series Id '${token.series}' already exists!")

        val dbToken = PersistentLogin().apply {
            this.series = token.series
            this.token = token.tokenValue
            this.username = token.username
            this.lastUsed = token.date
        }

        persistentLoginRepository.save(dbToken)
    }

    @Transactional
    override fun updateToken(series: String, tokenValue: String, lastUsed: Date) {
        val token = persistentLoginRepository.findBySeries(series) ?: return
        token.token = tokenValue
        token.lastUsed = lastUsed
        persistentLoginRepository.save(token)
    }

    override fun getTokenForSeries(seriesId: String): PersistentRememberMeToken? {
        return persistentLoginRepository.findBySeries(seriesId)?.let {
            PersistentRememberMeToken(it.username, it.series, it.token, it.lastUsed)
        }
    }

    @Transactional
    override fun removeUserTokens(username: String) {
        persistentLoginRepository.deleteAllByUsername(username)
    }
}
