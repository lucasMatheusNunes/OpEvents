package br.com.zup.op.events.infra.validation

import org.springframework.web.bind.MethodArgumentNotValidException
import javax.validation.ConstraintViolation
import javax.validation.Validation

class AnnotationFieldsValidation {

    @Throws(MethodArgumentNotValidException::class)
    fun <T> validFields(objectData: T) {

        val violations: Set<ConstraintViolation<T>> =
            Validation.buildDefaultValidatorFactory().validator.validate(objectData)

        if (violations.isNotEmpty()) {
            val fieldErrors = ArrayList<FieldValidationCallback>()

            for (violation: ConstraintViolation<T> in violations) {
                fieldErrors.add(
                    FieldValidationCallback(
                        violation.propertyPath.toString(),
                        violation.message
                    )
                )
            }

            throw InvalidFieldException(ApiFieldError("", fieldErrors))
        }
    }
}