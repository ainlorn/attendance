package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.web.request.SignUpRequest
import org.springframework.security.access.prepost.PreAuthorize

interface UserService {
    fun register(request: SignUpRequest): UserDto

    @PreAuthorize("isAuthenticated()")
    fun getCurrentUserInfo(): UserDto

    @PreAuthorize("isAuthenticated()")
    fun isStudent(): Boolean

    @PreAuthorize("isAuthenticated()")
    fun isTrainer(): Boolean

    @PreAuthorize("isAuthenticated()")
    fun isAdmin(): Boolean
}
