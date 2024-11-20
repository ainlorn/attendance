package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.LessonPricePeriodDto
import com.midgetspinner31.attendance.web.request.LessonPricePeriodRequest
import org.springframework.security.access.prepost.PreAuthorize
import java.time.OffsetDateTime
import java.util.*

interface LessonPricePeriodService {

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun createPeriod(groupId: UUID, request: LessonPricePeriodRequest): LessonPricePeriodDto

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun updatePeriod(groupId: UUID, periodId: UUID, request: LessonPricePeriodRequest): LessonPricePeriodDto

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun deletePeriod(groupId: UUID, periodId: UUID)

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getPeriod(groupId: UUID, periodId: UUID): LessonPricePeriodDto

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getPeriodByTime(groupId: UUID, time: OffsetDateTime): LessonPricePeriodDto

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getPeriods(groupId: UUID): List<LessonPricePeriodDto>

}
