package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.GroupDto
import com.midgetspinner31.attendance.service.GroupService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@ApiV1
@RequestMapping("/group")
class GroupController(private val groupService: GroupService) {
    //TODO: set permissions only for admin
    @PostMapping("/create")
    fun createGroup(groupName: String): GroupDto {
        return groupService.createGroup(groupName)
    }

    @GetMapping("/get/{id}")
    fun getGroup(@PathVariable id: UUID): GroupDto {
        return groupService.getGroup(id)
    }

    @GetMapping("/get")
    fun getAllGroups(): List<GroupDto> {
        return groupService.getGroups()
    }

}