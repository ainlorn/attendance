package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.LessonPricePeriodDto
import com.midgetspinner31.attendance.service.LessonPricePeriodService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.request.LessonPricePeriodRequest
import com.midgetspinner31.attendance.web.response.EmptyResponse
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*

@ApiV1
class LessonPricePeriodController(
    val lessonPricePeriodService: LessonPricePeriodService
) {
    @PostMapping("/groups/{groupId}/prices")
    fun createPeriod(@PathVariable groupId: UUID,
                     @Valid @RequestBody request: LessonPricePeriodRequest): ItemResponse<LessonPricePeriodDto> {
        return ItemResponse(lessonPricePeriodService.createPeriod(groupId, request))
    }

    @PatchMapping("/groups/{groupId}/prices/{periodId}")
    fun updatePeriod(@PathVariable groupId: UUID,
                     @PathVariable periodId: UUID,
                     @Valid @RequestBody request: LessonPricePeriodRequest): ItemResponse<LessonPricePeriodDto> {
        return ItemResponse(lessonPricePeriodService.updatePeriod(groupId, periodId, request))
    }

    @DeleteMapping("/groups/{groupId}/prices/{periodId}")
    fun deletePeriod(@PathVariable groupId: UUID,
                     @PathVariable periodId: UUID): EmptyResponse {
        lessonPricePeriodService.deletePeriod(groupId, periodId)
        return EmptyResponse()
    }

    @GetMapping("/groups/{groupId}/prices/{periodId}")
    fun getPeriod(@PathVariable groupId: UUID,
                  @PathVariable periodId: UUID): ItemResponse<LessonPricePeriodDto> {
        return ItemResponse(lessonPricePeriodService.getPeriod(groupId, periodId))
    }

    @GetMapping("/groups/{groupId}/prices")
    fun getPeriods(@PathVariable groupId: UUID,
                   @RequestParam(required = false) time: OffsetDateTime?): ListResponse<LessonPricePeriodDto> {
        return if (time == null) {
            ListResponse(lessonPricePeriodService.getPeriods(groupId))
        } else {
            ListResponse(listOf(lessonPricePeriodService.getPeriodByTime(groupId, time)))
        }
    }
}
