package com.midgetspinner31.attendance.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "attendance.bill")
class BillProperties {
    var savePath: String? = null
    var url: String? = null
}
