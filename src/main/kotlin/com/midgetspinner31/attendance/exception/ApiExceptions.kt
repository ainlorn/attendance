package com.midgetspinner31.attendance.exception

import com.midgetspinner31.attendance.enumerable.StatusCode

open class ApiException : RuntimeException {
    var status: StatusCode

    constructor(status: StatusCode) : super() {
        this.status = status
    }

    constructor(status: StatusCode, message: String) : super(message) {
        this.status = status
    }

    constructor(status: StatusCode, message: String, cause: Throwable) : super(message, cause) {
        this.status = status
    }

    constructor(status: StatusCode, cause: Throwable) : super(cause) {
        this.status = status
    }
}

class EmailInUseException : ApiException(StatusCode.EMAIL_IN_USE)
class LoginInUseException : ApiException(StatusCode.LOGIN_IN_USE)
class PhoneInUseException : ApiException(StatusCode.PHONE_IN_USE)
class UserNotFoundException : ApiException(StatusCode.USER_NOT_FOUND)
class GroupNotFoundException : ApiException(StatusCode.GROUP_NOT_FOUND)
