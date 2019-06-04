package br.com.zup.op.events.infra.validation

import com.fasterxml.jackson.databind.ObjectMapper
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PayloadValidator : ConstraintValidator<PayloadValid, String> {

    object PayloadValidator{
        val mapper = ObjectMapper()
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        PayloadValidator.mapper.readTree(value)
        return true
    }
}