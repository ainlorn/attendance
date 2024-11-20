package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.dao.LessonRepository
import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.Admin
import com.midgetspinner31.attendance.db.entity.Lesson
import com.midgetspinner31.attendance.db.entity.Student
import com.midgetspinner31.attendance.db.entity.Trainer
import com.midgetspinner31.attendance.dto.LessonDto
import com.midgetspinner31.attendance.dto.LessonWithGroupDto
import com.midgetspinner31.attendance.exception.IncorrectTimeRangeException
import com.midgetspinner31.attendance.service.LessonService
import com.midgetspinner31.attendance.util.TimeUtils
import com.midgetspinner31.attendance.web.request.ScheduleBatchRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : LessonService {
    override fun getCurrentWeekLessons(groupId: UUID): List<LessonDto> {
        val startTime = TimeUtils.getStartOfWeek()
        val endTime = startTime.plusDays(7)
        return getLessonsInPeriod(groupId, startTime, endTime)
    }

    override fun getLessonsInPeriod(
        groupId: UUID,
        startTime: OffsetDateTime,
        endTime: OffsetDateTime
    ): List<LessonDto> {
        return lessonRepository.findByGroupInTimeInterval(groupId, startTime, endTime).map { it.toDto() }
    }

    @Transactional
    override fun scheduleLesson(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): LessonDto {
        if (endTime <= startTime) throw IncorrectTimeRangeException()
        val lesson = Lesson().apply {
            this.group = groupRepository.getReferenceById(groupId)
            this.startTime = startTime
            this.endTime = endTime
        }
        return lessonRepository.save(lesson).toDto()
    }

    @Transactional
    override fun scheduleLessonsBatch(groupId: UUID, request: ScheduleBatchRequest): List<LessonDto> {
        val group = groupRepository.getReferenceById(groupId)
        val periodStart = request.startTime!!
        val periodEnd = request.endTime!!
        if (periodEnd <= periodStart) throw IncorrectTimeRangeException()

        var currentWeekStart = TimeUtils.getStartOfWeek(periodStart)
        val lessons = mutableListOf<LessonDto>()
        while (currentWeekStart < periodEnd) {
            for (it in request.scheduledLessons!!) {
                val startTime =
                    currentWeekStart.plusDays(it.day!! - 1L).plusSeconds(it.startTime!!.toSecondOfDay().toLong())
                val endTime =
                    currentWeekStart.plusDays(it.day!! - 1L).plusSeconds(it.endTime!!.toSecondOfDay().toLong())
                if (endTime <= startTime) throw IncorrectTimeRangeException()
                if (startTime > periodEnd || endTime < periodStart) continue

                val lesson = Lesson().apply {
                    this.group = group
                    this.startTime = startTime
                    this.endTime = endTime
                }
                lessons.add(lessonRepository.save(lesson).toDto())
            }
            currentWeekStart = currentWeekStart.plusDays(7)
        }
        return lessons
    }

    override fun getMyCurrentWeekLessons(): List<LessonWithGroupDto> {
        val startTime = TimeUtils.getStartOfWeek()
        val endTime = startTime.plusDays(7)
        return getMyLessonsInPeriod(startTime, endTime)
    }

    override fun getMyLessonsInPeriod(startTime: OffsetDateTime, endTime: OffsetDateTime): List<LessonWithGroupDto> {
        val groups = when (val user = userRepository.getCurrentUser()!!) {
            is Student -> groupRepository.findByStudent(user)
            is Trainer -> groupRepository.findByTrainer(user)
            is Admin -> groupRepository.findAll()
            else -> throw IllegalStateException()
        }

        return lessonRepository
            .findByGroupsInTimeInterval(groups.map { it.id!! }, startTime, endTime)
            .map { it.toDtoWithGroup() }
    }
}
