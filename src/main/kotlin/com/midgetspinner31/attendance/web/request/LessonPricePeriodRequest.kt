package com.midgetspinner31.attendance.web.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime

class LessonPricePeriodRequest(
    startTime: OffsetDateTime? = null,
    endTime: OffsetDateTime? = null,
    price: Long? = null
) : ApiRequest() {
    @NotNull
    var startTime = startTime

    @NotNull
    var endTime = endTime

    @NotNull
    @Min(0)
    var price = price
}
