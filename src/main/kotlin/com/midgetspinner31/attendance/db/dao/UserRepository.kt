package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    @Query("select u from User u where u.login = ?#{authenticated ? principal.username : null}")
    fun getCurrentUser(): User?

    fun findByEmail(email: String): User?

    fun findByLogin(login: String): User?

    fun findByPhone(phone: String): User?

    fun existsByLogin(login: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhone(phone: String): Boolean
}
