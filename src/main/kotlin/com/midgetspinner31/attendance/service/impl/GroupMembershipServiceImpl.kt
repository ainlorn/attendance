package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupMembershipRepository
import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.dao.StudentRepository
import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.GroupMembership
import com.midgetspinner31.attendance.db.entity.Student
import com.midgetspinner31.attendance.db.entity.Trainer
import com.midgetspinner31.attendance.dto.StudentPublicDto
import com.midgetspinner31.attendance.exception.AccessDeniedException
import com.midgetspinner31.attendance.exception.GroupNotFoundException
import com.midgetspinner31.attendance.exception.MemberAlreadyInGroupException
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.GroupMembershipService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class GroupMembershipServiceImpl(
    private val groupMembershipRepository: GroupMembershipRepository,
    private val studentRepository: StudentRepository,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : GroupMembershipService {
    @Transactional
    override fun addMemberToGroup(groupId: UUID, memberId: UUID) {
        val user = userRepository.getCurrentUser()!!
        val group = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()

        if (user is Trainer && user.id != group.trainer?.id) {
            throw AccessDeniedException()
        }

        val student = studentRepository.findByIdOrNull(memberId) ?: throw UserNotFoundException()

        val existingMembership = groupMembershipRepository.findActiveByGroupIdAndStudentId(groupId, memberId)
        if (existingMembership != null) {
            throw MemberAlreadyInGroupException()
        }

        val groupMembership = GroupMembership().apply {
            this.group = group
            this.student = student
            this.active = true
        }

        groupMembershipRepository.save(groupMembership)
    }

    @Transactional
    override fun removeMemberFromGroup(groupId: UUID, memberId: UUID) {
        val user = userRepository.getCurrentUser()!!
        val group = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()

        if (user is Trainer && user.id != group.trainer?.id) {
            throw AccessDeniedException()
        }

        val membership = groupMembershipRepository.findActiveByGroupIdAndStudentId(groupId, memberId)
            ?: throw UserNotFoundException()

        membership.active = false
    }

    override fun getGroupStudents(groupId: UUID): List<StudentPublicDto> {
        val user = userRepository.getCurrentUser()!!
        val group = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()

        if (user is Trainer && user.id != group.trainer?.id) {
            throw AccessDeniedException()
        }
        if (user is Student && !isStudentInGroup(groupId, user.id!!)) {
            throw AccessDeniedException()
        }

        return groupMembershipRepository.findActiveByGroupId(groupId).map { it.student!!.toPublicDto() }
    }

    override fun isStudentInGroup(groupId: UUID, studentId: UUID): Boolean {
        val membership = groupMembershipRepository.findByGroupIdAndStudentId(groupId, studentId)
            ?: return false
        return membership.active!!
    }
}
