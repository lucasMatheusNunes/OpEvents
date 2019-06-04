package br.com.zup.op.events.infra.validation

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PayloadValidator : ConstraintValidator<PayloadValid, String> {

    object PayloadValidator{
        val mapper = ObjectMapper()
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        try {
            PayloadValidator.mapper.readTree(value)
        }catch (e: JsonParseException){
            return false
        }
        return true
    }
}