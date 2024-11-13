package com.midgetspinner31.attendance.web.request

import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime

class ScheduleLessonRequest(
    startTime: OffsetDateTime? = null,
    endTime: OffsetDateTime? = null
) : ApiRequest() {
    @NotNull
    var startTime = startTime

    @NotNull
    var endTime = endTime
}
