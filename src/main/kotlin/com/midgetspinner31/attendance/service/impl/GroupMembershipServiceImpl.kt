package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupMembershipRepository
import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.dao.StudentRepository
import com.midgetspinner31.attendance.db.entity.GroupMembership
import com.midgetspinner31.attendance.exception.GroupNotFoundException
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.GroupMembershipService
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class GroupMembershipServiceImpl(
    private val groupMembershipRepository: GroupMembershipRepository,
    private val studentRepository: StudentRepository,
    private val groupRepository: GroupRepository
) : GroupMembershipService {
    override fun addMemberToGroup(groupId: UUID, memberId: UUID) {
        val group = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()
        val student = studentRepository.findByIdOrNull(memberId) ?: throw UserNotFoundException()

        val groupMembership = GroupMembership(group = group, student = student)

        groupMembershipRepository.save(groupMembership)
    }

    override fun removeMemberFromGroup(groupId: UUID, memberId: UUID) {
        val membership: GroupMembership? = groupMembershipRepository.findByGroupIdAndStudentId(groupId, memberId)

        if (membership == null || !membership.active) {
            throw UserNotFoundException()
        }
        membership.active = false

        groupMembershipRepository.save(membership)
    }
}