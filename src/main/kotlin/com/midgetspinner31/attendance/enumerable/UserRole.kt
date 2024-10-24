package com.midgetspinner31.attendance.enumerable

enum class UserRole(val authority: String) {
    STUDENT("USER_STUDENT"),
    TRAINER("USER_TRAINER"),
    ADMIN("USER_ADMIN");

    companion object {
        const val STUDENT_VALUE = "STUDENT"
        const val TRAINER_VALUE = "TRAINER"
        const val ADMIN_VALUE = "ADMIN"
    }
}
