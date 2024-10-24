package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface StudentRepository : JpaRepository<Student, UUID> {
    @Query("select s from Student s where s.login = ?#{authenticated ? principal.username : null}")
    fun getCurrentStudent(): Student?
}
