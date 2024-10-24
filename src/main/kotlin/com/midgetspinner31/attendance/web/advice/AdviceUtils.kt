package com.midgetspinner31.attendance.web.advice

import com.midgetspinner31.attendance.enumerable.StatusCode
import com.midgetspinner31.attendance.web.response.ErrorResponse
import org.springframework.http.ResponseEntity

object AdviceUtils {
    fun createResponse(s: StatusCode): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(s.httpCode).body(ErrorResponse(s))
    }
}
