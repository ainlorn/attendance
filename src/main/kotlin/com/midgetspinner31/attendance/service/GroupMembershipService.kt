package com.midgetspinner31.attendance.service

import java.util.*

interface GroupMembershipService {

    fun addMemberToGroup(groupId: UUID, memberId: UUID)

    fun removeMemberFromGroup(groupId: UUID, memberId: UUID)
}