package mjss.dio.creditapplicationsystem.dto

import mjss.dio.creditapplicationsystem.entity.Customer
import java.math.BigDecimal

data class CustomerViewDto(
    val firstname: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val zipCode: String,
    val street: String
) {
    constructor(customer: Customer): this(
        firstname = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        income = customer.income,
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )
}
