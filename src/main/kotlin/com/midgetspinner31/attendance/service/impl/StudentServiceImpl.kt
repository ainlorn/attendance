package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.StudentRepository
import com.midgetspinner31.attendance.dto.StudentDto
import com.midgetspinner31.attendance.dto.StudentPublicDto
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.StudentService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository
) : StudentService {
    override fun getAllStudents(): List<StudentPublicDto> {
        return studentRepository.findAll().map { it.toPublicDto() }
    }

    override fun getStudent(studentId: UUID): StudentDto {
        return studentRepository.findByIdOrNull(studentId)?.toDto() ?: throw UserNotFoundException()
    }
}
