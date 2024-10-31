package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.StudentDto
import com.midgetspinner31.attendance.dto.StudentPublicDto
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

interface StudentService {
    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun getAllStudents(): List<StudentPublicDto>

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun getStudent(studentId: UUID): StudentDto
}
