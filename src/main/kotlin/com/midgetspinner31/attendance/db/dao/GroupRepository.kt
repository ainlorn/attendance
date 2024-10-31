package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Group
import com.midgetspinner31.attendance.db.entity.Student
import com.midgetspinner31.attendance.db.entity.Trainer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupRepository : JpaRepository<Group, UUID> {
    fun findByTrainer(trainer: Trainer): List<Group>

    @Query("select g " +
            "from Group g join GroupMembership gm on g = gm.group " +
            "where gm.active = true and gm.student = :student")
    fun findByStudent(student: Student): List<Group>
}
