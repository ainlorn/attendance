package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupRepository
import com.midgetspinner31.attendance.db.dao.LessonPricePeriodRepository
import com.midgetspinner31.attendance.db.entity.LessonPricePeriod
import com.midgetspinner31.attendance.dto.LessonPricePeriodDto
import com.midgetspinner31.attendance.exception.IncorrectTimeRangeException
import com.midgetspinner31.attendance.exception.PricePeriodNotFoundException
import com.midgetspinner31.attendance.service.LessonPricePeriodService
import com.midgetspinner31.attendance.web.request.LessonPricePeriodRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class LessonPricePeriodServiceImpl(
    private val groupRepository: GroupRepository,
    private val lessonPricePeriodRepository: LessonPricePeriodRepository
) : LessonPricePeriodService {

    @Transactional
    override fun createPeriod(groupId: UUID, request: LessonPricePeriodRequest): LessonPricePeriodDto {
        if (request.endTime!! <= request.startTime!!) throw IncorrectTimeRangeException()
        val period = LessonPricePeriod().apply {
            this.group = groupRepository.getReferenceById(groupId)
            this.startTime = request.startTime
            this.endTime = request.endTime
            this.price = request.price
        }
        return lessonPricePeriodRepository.save(period).toDto()
    }

    @Transactional
    override fun updatePeriod(groupId: UUID, periodId: UUID, request: LessonPricePeriodRequest): LessonPricePeriodDto {
        val period = lessonPricePeriodRepository.findByGroupIdAndId(groupId, periodId)
            ?: throw PricePeriodNotFoundException()
        period.apply {
            this.startTime = request.startTime
            this.endTime = request.endTime
            this.price = request.price
        }
        return lessonPricePeriodRepository.save(period).toDto()
    }

    @Transactional
    override fun deletePeriod(groupId: UUID, periodId: UUID) {
        val period = lessonPricePeriodRepository.findByGroupIdAndId(groupId, periodId)
            ?: throw PricePeriodNotFoundException()
        lessonPricePeriodRepository.delete(period)
    }

    override fun getPeriod(groupId: UUID, periodId: UUID): LessonPricePeriodDto {
        val period = lessonPricePeriodRepository.findByGroupIdAndId(groupId, periodId)
            ?: throw PricePeriodNotFoundException()
        return period.toDto()
    }

    override fun getPeriodByTime(groupId: UUID, time: OffsetDateTime): LessonPricePeriodDto {
        val period = lessonPricePeriodRepository.findByGroupAndTime(groupId, time)
            ?: throw PricePeriodNotFoundException()
        return period.toDto()
    }

    override fun getPeriods(groupId: UUID): List<LessonPricePeriodDto> {
        return lessonPricePeriodRepository.findAllByGroup(groupId).map { it.toDto() }
    }
}
