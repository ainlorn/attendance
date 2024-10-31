package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupMembershipRepository
import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.Group
import com.midgetspinner31.attendance.db.entity.Group.Companion.toDto
import com.midgetspinner31.attendance.db.entity.Student
import com.midgetspinner31.attendance.db.entity.Trainer
import com.midgetspinner31.attendance.dto.GroupDto
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.exception.AccessDeniedException
import com.midgetspinner31.attendance.exception.GroupNotFoundException
import com.midgetspinner31.attendance.service.GroupMembershipService
import com.midgetspinner31.attendance.service.GroupService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val groupMembershipService: GroupMembershipService,
    private val userRepository: UserRepository
) : GroupService {
    @Transactional
    override fun createGroup(groupName: String): GroupDto {
        val user = userRepository.getCurrentUser()!!

        val group = Group().apply {
            this.name = groupName

            if (user is Trainer) {
                this.trainer = user
            }
        }
        groupRepository.save(group)
        return group.toDto()
    }

    override fun getGroup(groupId: UUID): GroupDto {
        val user = userRepository.getCurrentUser()!!
        val group = groupRepository.findByIdOrNull(groupId)?.toDto() ?: throw GroupNotFoundException()

        if (user is Student && !groupMembershipService.isStudentInGroup(groupId, user.id!!)) {
            throw AccessDeniedException()
        }

        return group
    }

    override fun getGroups(): List<GroupDto> {
        return groupRepository.findAll().map { it.toDto() }
    }

    override fun getMyGroups(): List<GroupDto> {
        return when (val user = userRepository.getCurrentUser()!!) {
            is Trainer -> groupRepository.findByTrainer(user).map { it.toDto() }
            is Student -> groupRepository.findByStudent(user).map { it.toDto() }
            else -> throw AccessDeniedException()
        }
    }
}
