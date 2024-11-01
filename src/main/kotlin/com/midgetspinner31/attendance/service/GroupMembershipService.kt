package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.StudentPublicDto
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

interface GroupMembershipService {
    @PreAuthorize("isAuthenticated()")
    fun getGroupStudents(groupId: UUID): List<StudentPublicDto>

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun addMemberToGroup(groupId: UUID, memberId: UUID)

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun removeMemberFromGroup(groupId: UUID, memberId: UUID)

    fun isStudentInGroup(groupId: UUID, studentId: UUID): Boolean
}
