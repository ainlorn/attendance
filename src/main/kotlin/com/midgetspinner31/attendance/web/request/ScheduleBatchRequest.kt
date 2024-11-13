package com.midgetspinner31.attendance.web.request

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalTime
import java.time.OffsetDateTime

class ScheduleBatchRequest(
    @NotNull
    var startTime: OffsetDateTime?,

    @NotNull
    var endTime: OffsetDateTime?,

    @NotEmpty
    @Valid
    var scheduledLessons: List<ScheduledLesson>?
) : ApiRequest()

class ScheduledLesson(
    @NotNull
    @Min(1)
    @Max(7)
    var day: Int?,

    @NotNull
    var startTime: LocalTime?,

    @NotNull
    var endTime: LocalTime?
)
