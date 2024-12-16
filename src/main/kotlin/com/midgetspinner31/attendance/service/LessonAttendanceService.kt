package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.LessonWithAttendanceDto
import com.midgetspinner31.attendance.dto.StudentAttendanceDto
import org.springframework.security.access.prepost.PreAuthorize
import java.time.OffsetDateTime
import java.util.*

interface LessonAttendanceService {
    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getAttendanceForLesson(groupId: UUID, lessonId: UUID): List<StudentAttendanceDto>

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun markAttendance(groupId: UUID, lessonId: UUID, studentId: UUID): StudentAttendanceDto

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun unmarkAttendance(groupId: UUID, lessonId: UUID, studentId: UUID)

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getAttendanceInPeriod(groupId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): List<LessonWithAttendanceDto>

    @PreAuthorize("@groupMembershipService.currentUserHasReadAccess(#groupId)")
    fun getCurrentWeekAttendance(groupId: UUID): List<LessonWithAttendanceDto>
}
