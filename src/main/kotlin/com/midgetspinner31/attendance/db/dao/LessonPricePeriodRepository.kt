package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.LessonPricePeriod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*

@Repository
interface LessonPricePeriodRepository : JpaRepository<LessonPricePeriod, UUID> {

    fun findByGroupIdAndId(groupId: UUID, id: UUID): LessonPricePeriod?

    @Query("select lpp from LessonPricePeriod lpp where lpp.group.id = :groupId " +
            "and :time between lpp.startTime and lpp.endTime")
    fun findByGroupAndTime(groupId: UUID, time: OffsetDateTime): LessonPricePeriod?

    @Query("select lpp from LessonPricePeriod lpp where lpp.group.id = :groupId")
    fun findAllByGroup(groupId: UUID): List<LessonPricePeriod>
}
