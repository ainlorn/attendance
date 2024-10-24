package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.PersistentLogin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PersistentLoginRepository : JpaRepository<PersistentLogin, UUID> {
    fun findBySeries(series: String): PersistentLogin?
    fun existsBySeries(series: String): Boolean
    fun deleteAllByUsername(username: String)
}
