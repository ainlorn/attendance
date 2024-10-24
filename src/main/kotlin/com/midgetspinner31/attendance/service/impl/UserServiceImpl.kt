package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.User
import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.exception.EmailInUseException
import com.midgetspinner31.attendance.exception.LoginInUseException
import com.midgetspinner31.attendance.exception.PhoneInUseException
import com.midgetspinner31.attendance.service.UserService
import com.midgetspinner31.attendance.web.request.SignUpRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun register(request: SignUpRequest): UserDto {
        if (userRepository.existsByEmail(request.email!!))
            throw EmailInUseException()
        if (userRepository.existsByLogin(request.login!!))
            throw LoginInUseException()
        if (userRepository.existsByPhone(request.phone!!))
            throw PhoneInUseException()

        request.password = passwordEncoder.encode(request.password)
        val user = userRepository.save(User.fromSignUpRequest(request))
        return user.toDto()
    }

    override fun getCurrentUserInfo(): UserDto {
        return userRepository.getCurrentUser()!!.toDto()
    }
}
