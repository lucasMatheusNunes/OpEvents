package br.com.zup.op.events.infra.validation

class ApiFieldError (
    var message : String,
    var errors : List<FieldValidationCallback>
) {
    constructor(message: String, error: FieldValidationCallback) : this(message, arrayListOf<FieldValidationCallback>(error))
}