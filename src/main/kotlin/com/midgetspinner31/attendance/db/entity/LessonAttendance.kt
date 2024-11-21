package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "lesson_attendances", indexes = [
    Index(name = "idx_lessonattendance_lesson_id", columnList = "lesson_id"),
    Index(name = "idx_lessonattendance_student_id", columnList = "student_id")
],  uniqueConstraints = [
    UniqueConstraint(name = "uc_lessonattendance", columnNames = ["student_id", "lesson_id"])
])
class LessonAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    var student: Student? = null

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", nullable = false)
    var lesson: Lesson? = null
}
