package br.com.zup.op.events.infra.validation

class ApplicationField (
    var message : String?,
    var errors : List<FieldValidationCallback>? = null
)