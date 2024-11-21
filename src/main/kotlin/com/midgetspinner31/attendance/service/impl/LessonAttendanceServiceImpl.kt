package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupMembershipRepository
import com.midgetspinner31.attendance.db.dao.LessonAttendanceRepository
import com.midgetspinner31.attendance.db.dao.LessonRepository
import com.midgetspinner31.attendance.db.entity.LessonAttendance
import com.midgetspinner31.attendance.dto.StudentAttendanceDto
import com.midgetspinner31.attendance.exception.LessonNotFoundException
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.LessonAttendanceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class LessonAttendanceServiceImpl(
    private val lessonAttendanceRepository: LessonAttendanceRepository,
    private val lessonRepository: LessonRepository,
    private val groupMembershipRepository: GroupMembershipRepository
) : LessonAttendanceService {
    override fun getAttendanceForLesson(groupId: UUID, lessonId: UUID): List<StudentAttendanceDto> {
        val lesson = lessonRepository.findByGroupIdAndId(groupId, lessonId)
            ?: throw LessonNotFoundException()

        val memberships = groupMembershipRepository.findAllByGroupIdAndDate(groupId, lesson.startTime!!)
        val attendances = lessonAttendanceRepository.findAllByLessonId(lessonId)

        val result = mutableListOf<StudentAttendanceDto>()
        val addedIds = mutableSetOf<UUID>()

        attendances.forEach {
            result.add(
                StudentAttendanceDto(
                    it.student!!.fullName,
                    it.student!!.id,
                    true
                )
            )
            addedIds.add(it.student!!.id!!)
        }
        memberships.filter { it.student!!.id!! !in addedIds }.forEach {
            result.add(
                StudentAttendanceDto(
                    it.student!!.fullName,
                    it.student!!.id,
                    false
                )
            )
        }
        result.sortBy { it.studentFullName }

        return result
    }

    @Transactional
    override fun markAttendance(groupId: UUID, lessonId: UUID, studentId: UUID): StudentAttendanceDto {
        val lesson = lessonRepository.findByGroupIdAndId(groupId, lessonId)
            ?: throw LessonNotFoundException()
        val membership = groupMembershipRepository.findByGroupIdAndStudentIdAndDate(groupId, studentId, lesson.startTime!!)
            ?: throw UserNotFoundException()

        val attendance = lessonAttendanceRepository.findByLessonIdAndStudentId(lessonId, studentId)
            ?: lessonAttendanceRepository.save(LessonAttendance().apply {
                this.student = membership.student
                this.lesson = lesson
            })

        return StudentAttendanceDto(
            attendance.student!!.fullName,
            attendance.student!!.id,
            true
        )
    }

    @Transactional
    override fun unmarkAttendance(groupId: UUID, lessonId: UUID, studentId: UUID) {
        val lesson = lessonRepository.findByGroupIdAndId(groupId, lessonId)
            ?: throw LessonNotFoundException()
        val membership = groupMembershipRepository.findByGroupIdAndStudentIdAndDate(groupId, studentId, lesson.startTime!!)
            ?: throw UserNotFoundException()

        val attendance = lessonAttendanceRepository.findByLessonIdAndStudentId(lessonId, studentId)
        if (attendance != null) {
            lessonAttendanceRepository.delete(attendance)
        }
    }
}
