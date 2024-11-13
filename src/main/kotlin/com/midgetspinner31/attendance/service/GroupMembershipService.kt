package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.StudentPublicDto
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

interface GroupMembershipService {
    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getGroupStudents(groupId: UUID): List<StudentPublicDto>

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun addMemberToGroup(groupId: UUID, memberId: UUID)

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun removeMemberFromGroup(groupId: UUID, memberId: UUID)

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun isStudentInGroup(groupId: UUID, studentId: UUID): Boolean

    @PreAuthorize("isAuthenticated()")
    fun currentUserHasReadAccess(groupId: UUID): Boolean

    @PreAuthorize("isAuthenticated()")
    fun currentUserHasWriteAccess(groupId: UUID): Boolean
}
