package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.enumerable.StatusCode
import com.midgetspinner31.attendance.service.UserService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.request.SignInRequest
import com.midgetspinner31.attendance.web.request.SignUpRequest
import com.midgetspinner31.attendance.web.response.ApiResponse
import com.midgetspinner31.attendance.web.response.EmptyResponse
import com.midgetspinner31.attendance.web.response.ErrorResponse
import com.midgetspinner31.attendance.web.response.ItemResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val rememberMeServices: RememberMeServices
) {
    /**
     * Зарегистрировать аккаунт
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: SignUpRequest): ItemResponse<UserDto> {
        return ItemResponse(userService.register(request))
    }

    /**
     * Войти в аккаунт
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody authRequest: SignInRequest,
              request: HttpServletRequest, response: HttpServletResponse) : ResponseEntity<ApiResponse> {
        val token = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        token.details = WebAuthenticationDetails(request)

        try {
            val auth = authenticationManager.authenticate(token)
            val securityContext = SecurityContextHolder.getContext()
            securityContext.authentication = auth
            if (auth != null && auth.isAuthenticated) {
                val session = request.getSession(true)
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext)
                rememberMeServices.loginSuccess(request, response, auth)
            } else {
                throw BadCredentialsException(null)
            }
        } catch (e: BadCredentialsException) {
            SecurityContextHolder.getContext().authentication = null

            val status = StatusCode.WRONG_CREDENTIALS
            return ResponseEntity
                .status(status.httpCode)
                .body(ErrorResponse(status))
        }

        return ResponseEntity.ok(ItemResponse(userService.getCurrentUserInfo()))
    }

    /**
     * Выйти из аккаунта
     */
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): EmptyResponse {
        SecurityContextHolder.getContext().authentication = null
        rememberMeServices.loginFail(request, response)
        return EmptyResponse()
    }

}
