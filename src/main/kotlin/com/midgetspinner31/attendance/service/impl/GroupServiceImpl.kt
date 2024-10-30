package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.entity.Group
import com.midgetspinner31.attendance.db.entity.Group.Companion.toDto
import com.midgetspinner31.attendance.dto.GroupDto
import com.midgetspinner31.attendance.exception.GroupNotFoundException
import com.midgetspinner31.attendance.service.GroupService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupServiceImpl(private val groupRepository: GroupRepository) : GroupService {
    override fun createGroup(groupName: String): GroupDto {
        val group = Group(name = groupName);
        groupRepository.save(group);
        return group.toDto();
    }

    override fun getGroup(groupId: UUID): GroupDto {
        return groupRepository.findByIdOrNull(groupId)?.toDto() ?: throw GroupNotFoundException()
    }

    override fun getGroups(): List<GroupDto> {
        return groupRepository.findAll().map { it.toDto() }
    }

}