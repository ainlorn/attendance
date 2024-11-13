package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.LessonDto
import com.midgetspinner31.attendance.service.LessonService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.request.ScheduleBatchRequest
import com.midgetspinner31.attendance.web.request.ScheduleLessonRequest
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*

@ApiV1
class LessonController(
    private val lessonService: LessonService
) {
    @GetMapping("/groups/{groupId}/schedule")
    fun getSchedule(
        @PathVariable groupId: UUID,
        @RequestParam(required = false) startTime: OffsetDateTime?,
        @RequestParam(required = false) endTime: OffsetDateTime?
    ): ListResponse<LessonDto> {
        return ListResponse(
            if (startTime != null && endTime != null) {
                lessonService.getLessonsInPeriod(groupId, startTime, endTime)
            } else {
                lessonService.getCurrentWeekLessons(groupId)
            }
        )
    }

    @PostMapping("/groups/{groupId}/schedule")
    fun scheduleLesson(
        @PathVariable groupId: UUID,
        @Valid @RequestBody request: ScheduleLessonRequest
    ): ItemResponse<LessonDto> {
        return ItemResponse(lessonService.scheduleLesson(groupId, request.startTime!!, request.endTime!!))
    }

    @PostMapping("/groups/{groupId}/schedule/batch")
    fun scheduleLesson(
        @PathVariable groupId: UUID,
        @Valid @RequestBody request: ScheduleBatchRequest
    ): ListResponse<LessonDto> {
        return ListResponse(lessonService.scheduleLessonsBatch(groupId, request))
    }
}
