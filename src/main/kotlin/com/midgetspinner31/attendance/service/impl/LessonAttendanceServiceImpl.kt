package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.*
import com.midgetspinner31.attendance.db.entity.LessonAttendance
import com.midgetspinner31.attendance.db.entity.Transaction
import com.midgetspinner31.attendance.dto.LessonWithAttendanceDto
import com.midgetspinner31.attendance.dto.StudentAttendanceDto
import com.midgetspinner31.attendance.enumerable.TransactionType
import com.midgetspinner31.attendance.exception.LessonNotFoundException
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.LessonAttendanceService
import com.midgetspinner31.attendance.service.LessonService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class LessonAttendanceServiceImpl(
    private val lessonAttendanceRepository: LessonAttendanceRepository,
    private val lessonRepository: LessonRepository,
    private val lessonService: LessonService,
    private val groupMembershipRepository: GroupMembershipRepository,
    private val transactionRepository: TransactionRepository,
    private val lessonPricePeriodRepository: LessonPricePeriodRepository
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
        val membership =
            groupMembershipRepository.findByGroupIdAndStudentIdAndDate(groupId, studentId, lesson.startTime!!)
                ?: throw UserNotFoundException()

        val attendance = lessonAttendanceRepository.findByLessonIdAndStudentId(lessonId, studentId)
            ?: lessonAttendanceRepository.save(LessonAttendance().apply {
                this.student = membership.student
                this.lesson = lesson
            })

        lessonPricePeriodRepository.findByGroupAndTime(lesson.group!!.id!!, lesson.startTime!!)?.let { price ->
            transactionRepository.findByTransactionTypeAndKey(TransactionType.LESSON_PAYMENT, attendance.id!!)
                ?: transactionRepository.save(Transaction().apply {
                    this.student = membership.student
                    this.transactionType = TransactionType.LESSON_PAYMENT
                    this.key = attendance.id
                    this.dt = lesson.startTime
                    this.sum = -price.price!!
                })
        }

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
        val membership =
            groupMembershipRepository.findByGroupIdAndStudentIdAndDate(groupId, studentId, lesson.startTime!!)
                ?: throw UserNotFoundException()

        lessonAttendanceRepository.findByLessonIdAndStudentId(lessonId, studentId)?.let { attendance ->
            lessonAttendanceRepository.delete(attendance)
            transactionRepository.findByTransactionTypeAndKey(TransactionType.LESSON_PAYMENT, attendance.id!!)
                ?.let { tx -> transactionRepository.delete(tx) }
        }
    }

    override fun getAttendanceInPeriod(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): List<LessonWithAttendanceDto> {
        return lessonService
            .getLessonsInPeriod(groupId, startTime, endTime)
            .map { LessonWithAttendanceDto(it, getAttendanceForLesson(groupId, it.id!!)) }
    }

    override fun getCurrentWeekAttendance(groupId: UUID): List<LessonWithAttendanceDto> {
        return lessonService
            .getCurrentWeekLessons(groupId)
            .map { LessonWithAttendanceDto(it, getAttendanceForLesson(groupId, it.id!!)) }
    }
}
