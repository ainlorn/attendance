package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime
import java.util.*

interface LessonRepository : JpaRepository<Lesson, UUID> {

    fun findByGroupIdAndId(groupId: UUID, id: UUID): Lesson?

    @Query("select l from Lesson l where l.group.id=:groupId and " +
            "(l.startTime between :startTime and :endTime or l.endTime between :startTime and :endTime)")
    fun findByGroupInTimeInterval(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): List<Lesson>

    @Query("select l from Lesson l where l.group.id in :groupId and " +
            "(l.startTime between :startTime and :endTime or l.endTime between :startTime and :endTime)")
    fun findByGroupsInTimeInterval(groupId: Collection<UUID>, startTime: OffsetDateTime, endTime: OffsetDateTime): List<Lesson>

}
