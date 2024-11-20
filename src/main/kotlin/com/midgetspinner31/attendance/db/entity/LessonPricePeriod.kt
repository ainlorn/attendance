package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.LessonPricePeriodDto
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "lesson_price_periods")
class LessonPricePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    var group: Group? = null

    @Column(name = "start_time", nullable = false)
    var startTime: OffsetDateTime? = null

    @Column(name = "end_time", nullable = false)
    var endTime: OffsetDateTime? = null

    @Column(name = "price", nullable = false)
    var price: Long? = null

    fun toDto(): LessonPricePeriodDto {
        return LessonPricePeriodDto(id, startTime, endTime, price)
    }

}
