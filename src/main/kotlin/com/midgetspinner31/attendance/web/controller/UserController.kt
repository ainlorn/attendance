package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.service.UserService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.response.ItemResponse
import org.springframework.web.bind.annotation.GetMapping

@ApiV1
class UserController(
    private val userService: UserService
) {
    /**
     * Получить информацию о текущем пользователе
     */
    @GetMapping("/me")
    fun me(): ItemResponse<UserDto> {
        return ItemResponse(userService.getCurrentUserInfo())
    }
}
