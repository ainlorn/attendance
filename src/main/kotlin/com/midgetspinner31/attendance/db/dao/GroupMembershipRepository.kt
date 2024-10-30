package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.GroupMembership
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupMembershipRepository : JpaRepository<GroupMembership, UUID> {
    fun findByGroupIdAndStudentId(groupId: UUID, studentId: UUID): GroupMembership?
}