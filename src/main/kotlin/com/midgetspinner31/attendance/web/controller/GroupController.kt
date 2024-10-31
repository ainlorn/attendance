package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.GroupDto
import com.midgetspinner31.attendance.dto.StudentPublicDto
import com.midgetspinner31.attendance.service.GroupMembershipService
import com.midgetspinner31.attendance.service.GroupService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.request.CreateGroupRequest
import com.midgetspinner31.attendance.web.response.EmptyResponse
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.util.*

@ApiV1
class GroupController(
    private val groupService: GroupService,
    private val groupMembershipService: GroupMembershipService
) {
    @PostMapping("/groups")
    fun createGroup(@Valid @RequestBody request: CreateGroupRequest): ItemResponse<GroupDto> {
        return ItemResponse(groupService.createGroup(request.name!!))
    }

    @GetMapping("/groups/{id}")
    fun getGroup(@PathVariable id: UUID): ItemResponse<GroupDto> {
        return ItemResponse(groupService.getGroup(id))
    }

    @GetMapping("/groups/{id}/students")
    fun getGroupStudents(@PathVariable id: UUID): ListResponse<StudentPublicDto> {
        return ListResponse(groupMembershipService.getGroupStudents(id))
    }

    @PutMapping("/groups/{groupId}/students/{studentId}")
    fun addStudentToGroup(@PathVariable groupId: UUID, @PathVariable studentId: UUID): EmptyResponse {
        groupMembershipService.addMemberToGroup(groupId, studentId)
        return EmptyResponse()
    }

    @DeleteMapping("/groups/{groupId}/students/{studentId}")
    fun removeStudentFromGroup(@PathVariable groupId: UUID, @PathVariable studentId: UUID): EmptyResponse {
        groupMembershipService.removeMemberFromGroup(groupId, studentId)
        return EmptyResponse()
    }

    @GetMapping("/groups")
    fun getAllGroups(): ListResponse<GroupDto> {
        return ListResponse(groupService.getGroups())
    }

    @GetMapping("/me/groups")
    fun getMyGroups(): ListResponse<GroupDto> {
        return ListResponse(groupService.getMyGroups())
    }
}
