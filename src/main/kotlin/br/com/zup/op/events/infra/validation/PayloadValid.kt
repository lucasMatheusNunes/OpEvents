package br.com.zup.op.events.infra.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [PayloadValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(value = AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class PayloadValid(
    val message: String = "Invalid Limit of Code",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)