package br.com.zup.op.events.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "person")
class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int,

    @Column(name = "name")
    @field:NotEmpty
    val name : String?,

    @Column(name="birthdate")
    @field:[NotEmpty Pattern(
        regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}\n",
        message = "invalid format for birthdate field"
    )]
    val birthdate : String?,

    @Column(name = "gender")
    @field:[NotEmpty Size(min = 1, max = 1) Pattern(
        regexp = "^([F|M|O]*)$",
        message = "sex field accept letter F, M or O only"
    )]
    //F - Female, M - Male, O - Other
    val gender : String?,

    @field:Email
    @Column(name = "email")
    val email : String?,

    @Column(name = "phone")
    val phone : String?
)