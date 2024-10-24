package com.midgetspinner31.attendance.aop

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class WebLoggingInterceptor {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Pointcut("within(@com.midgetspinner31.attendance.web.annotation.ApiV1 *)")
    fun apiController() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
              "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
              "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
              "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
              "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
              "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    fun requestMapping() {
    }

    @Pointcut("apiController() && requestMapping()")
    fun apiControllerRequestMapping() {
    }

    @Before("apiControllerRequestMapping()")
    fun logApiControllerRequestMapping() {
        val reqAttributes = RequestContextHolder.getRequestAttributes()
        if (reqAttributes !is ServletRequestAttributes)
            return
        val request = reqAttributes.request
        log.info("${request.remoteAddr} - ${request.method} ${request.requestURI}")
    }

}
