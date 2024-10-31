package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.StudentDto
import com.midgetspinner31.attendance.dto.StudentPublicDto
import com.midgetspinner31.attendance.service.StudentService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@ApiV1
class StudentController(
    private val studentService: StudentService
) {
    @GetMapping("/students")
    fun getAllStudents(): ListResponse<StudentPublicDto> {
        return ListResponse(studentService.getAllStudents())
    }

    @GetMapping("/students/{id}")
    fun getStudent(@PathVariable id: UUID): ItemResponse<StudentDto> {
        return ItemResponse(studentService.getStudent(id))
    }
}
