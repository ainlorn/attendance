package com.midgetspinner31.attendance.dto

import java.util.*

data class GroupDto(
    val id: UUID? = null,
    val name: String? = null,
    val trainer: TrainerDto? = null,
    val students: MutableList<StudentDto> = mutableListOf(),
    //TODO: add attendannce
)