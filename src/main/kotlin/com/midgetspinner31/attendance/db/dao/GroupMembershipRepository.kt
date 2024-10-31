package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.GroupMembership
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GroupMembershipRepository : JpaRepository<GroupMembership, UUID> {
    @Query("select gm from GroupMembership gm where gm.active = true and gm.group.id = :groupId")
    fun findActiveByGroupId(groupId: UUID): List<GroupMembership>

    fun findByGroupIdAndStudentId(groupId: UUID, studentId: UUID): GroupMembership?

    @Query("select gm from GroupMembership gm where gm.group.id = :groupId and gm.student.id = :studentId and gm.active = true")
    fun findActiveByGroupIdAndStudentId(groupId: UUID, studentId: UUID): GroupMembership?
}
