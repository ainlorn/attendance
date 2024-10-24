package com.midgetspinner31.attendance.web.request

class AdminSignUpRequest(
    login: String? = null,
    fullName: String? = null,
    email: String? = null,
    phone: String? = null,
    password: String? = null,
) : SignUpRequest(login, fullName, email, phone, password)
