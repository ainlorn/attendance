package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.LessonAttendance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LessonAttendanceRepository : JpaRepository<LessonAttendance, UUID> {
    fun findAllByLessonId(lessonId: UUID): List<LessonAttendance>

    fun findByLessonIdAndStudentId(lessonId: UUID, studentId: UUID): LessonAttendance?
}
