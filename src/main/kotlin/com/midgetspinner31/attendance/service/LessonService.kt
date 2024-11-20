package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.LessonDto
import com.midgetspinner31.attendance.dto.LessonWithGroupDto
import com.midgetspinner31.attendance.web.request.ScheduleBatchRequest
import org.springframework.security.access.prepost.PreAuthorize
import java.time.OffsetDateTime
import java.util.*

interface LessonService {

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getCurrentWeekLessons(groupId: UUID): List<LessonDto>

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getLessonsInPeriod(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): List<LessonDto>

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun scheduleLesson(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): LessonDto

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun scheduleLessonsBatch(groupId: UUID, request: ScheduleBatchRequest): List<LessonDto>

    @PreAuthorize("isAuthenticated()")
    fun getMyCurrentWeekLessons(): List<LessonWithGroupDto>

    @PreAuthorize("isAuthenticated()")
    fun getMyLessonsInPeriod(startTime: OffsetDateTime, endTime: OffsetDateTime): List<LessonWithGroupDto>
}
