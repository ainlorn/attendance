package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.GroupDto
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

interface GroupService {
    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun createGroup(groupName: String): GroupDto

    @PreAuthorize("isAuthenticated()")
    fun getGroup(groupId: UUID): GroupDto

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun getGroups(): List<GroupDto>

    @PreAuthorize("@userService.isTrainer() || @userService.isStudent()")
    fun getMyGroups(): List<GroupDto>
}
