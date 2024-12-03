package com.midgetspinner31.attendance.web.request

import jakarta.validation.constraints.NotNull
import java.util.*

class DepositRequest(
    studentId: UUID? = null,
    sum: Long? = null
) : ApiRequest() {
    @NotNull
    var studentId = studentId

    @NotNull
    var sum = sum
}
