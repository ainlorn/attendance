package com.midgetspinner31.attendance.validation.constraint

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.constraints.Pattern
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Pattern(regexp = "^\\+7\\d{10}$")
@Constraint(validatedBy = [])
@Target(FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, FIELD, ANNOTATION_CLASS, CONSTRUCTOR,
        VALUE_PARAMETER, CLASS, TYPE, TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class PhoneNumber(
    val message: String = "Not a valid phone number!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
