package com.midgetspinner31.attendance.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Value("\${security.remember-me-key}")
    private lateinit var rememberMeKey: String

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    protected fun securityFilterChain(
        httpSecurity: HttpSecurity, rememberMeServices: PersistentTokenBasedRememberMeServices?
    ): SecurityFilterChain {
        return httpSecurity
            .cors { config -> config.disable() }
            .csrf { config -> config.disable() } // по хорошему надо это настроить
            .authorizeHttpRequests { request -> request.anyRequest().permitAll() }
            .logout { config -> config.disable() }
            .rememberMe { rememberMe -> rememberMe.rememberMeServices(rememberMeServices) }
            .build()
    }

    @Bean
    fun rememberMeServices(
        userDetailsService: UserDetailsService?,
        persistentTokenRepository: PersistentTokenRepository?
    ): PersistentTokenBasedRememberMeServices {
        val rememberMe = PersistentTokenBasedRememberMeServices(
            rememberMeKey,
            userDetailsService,
            persistentTokenRepository
        )
        rememberMe.setTokenValiditySeconds(2592000)
        rememberMe.setTokenLength(64)
        rememberMe.setSeriesLength(64)
        rememberMe.setAlwaysRemember(true)
        return rememberMe
    }

    @Bean
    fun securityEvaluationContextExtension(): SecurityEvaluationContextExtension {
        return SecurityEvaluationContextExtension()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}
