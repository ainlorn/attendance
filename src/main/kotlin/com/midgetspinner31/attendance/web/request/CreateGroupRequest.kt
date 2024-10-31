package com.midgetspinner31.attendance.web.request

import jakarta.validation.constraints.NotEmpty

class CreateGroupRequest(
    name: String? = null
) : ApiRequest() {
    @NotEmpty
    var name = trim(name)
        set(value) { field = trim(name) }
}
