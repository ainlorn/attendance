package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.LessonWithAttendanceDto
import com.midgetspinner31.attendance.dto.StudentAttendanceDto
import com.midgetspinner31.attendance.service.LessonAttendanceService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.response.EmptyResponse
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*

@ApiV1
class LessonAttendanceController(
    private val lessonAttendanceService: LessonAttendanceService
) {
    @GetMapping("/groups/{groupId}/lessons/{lessonId}/attendance")
    fun getAttendanceForLesson(
        @PathVariable groupId: UUID,
        @PathVariable lessonId: UUID
    ): ListResponse<StudentAttendanceDto> {
        return ListResponse(lessonAttendanceService.getAttendanceForLesson(groupId, lessonId))
    }

    @PutMapping("/groups/{groupId}/lessons/{lessonId}/attendance/{studentId}")
    fun markAttendance(
        @PathVariable groupId: UUID,
        @PathVariable lessonId: UUID,
        @PathVariable studentId: UUID
    ): ItemResponse<StudentAttendanceDto> {
        return ItemResponse(lessonAttendanceService.markAttendance(groupId, lessonId, studentId))
    }

    @DeleteMapping("/groups/{groupId}/lessons/{lessonId}/attendance/{studentId}")
    fun unmarkAttendance(
        @PathVariable groupId: UUID,
        @PathVariable lessonId: UUID,
        @PathVariable studentId: UUID
    ): EmptyResponse {
        lessonAttendanceService.unmarkAttendance(groupId, lessonId, studentId)
        return EmptyResponse()
    }

    @GetMapping("/groups/{groupId}/attendance")
    fun getAttendanceForPeriod(
        @PathVariable groupId: UUID,
        @RequestParam(required = false) startTime: OffsetDateTime?,
        @RequestParam(required = false) endTime: OffsetDateTime?
    ): ListResponse<LessonWithAttendanceDto> {
        return ListResponse(
            if (startTime != null && endTime != null) {
                lessonAttendanceService.getAttendanceInPeriod(groupId, startTime, endTime)
            } else {
                lessonAttendanceService.getCurrentWeekAttendance(groupId)
            }
        )
    }
}
