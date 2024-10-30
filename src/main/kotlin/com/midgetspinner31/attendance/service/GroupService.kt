package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.GroupDto
import java.util.*

interface GroupService {
    fun createGroup(groupName: String): GroupDto

    fun getGroup(groupId: UUID): GroupDto

    fun getGroups(): List<GroupDto>
}