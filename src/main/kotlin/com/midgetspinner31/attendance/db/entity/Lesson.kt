package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.LessonDto
import com.midgetspinner31.attendance.dto.LessonWithGroupDto
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "lessons", indexes = [
    Index(name = "idx_lesson_group_id", columnList = "group_id"),
    Index(name = "idx_lesson_start_time", columnList = "start_time"),
    Index(name = "idx_lesson_end_time", columnList = "end_time")
])
class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    var group: Group? = null

    @Column(name = "start_time", nullable = false)
    var startTime: OffsetDateTime? = null

    @Column(name = "end_time", nullable = false)
    var endTime: OffsetDateTime? = null

    fun toDto(): LessonDto {
        return LessonDto(id, startTime, endTime)
    }

    fun toDtoWithGroup(): LessonWithGroupDto {
        return LessonWithGroupDto(id, group?.id, startTime, endTime)
    }
}
