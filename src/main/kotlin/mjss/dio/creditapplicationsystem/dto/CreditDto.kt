package mjss.dio.creditapplicationsystem.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import mjss.dio.creditapplicationsystem.entity.Credit
import mjss.dio.creditapplicationsystem.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Invalid input")
    val creditValue: BigDecimal,

    @field:Future(message = "The date must be in the future")
    val dayFistOfInstallment: LocalDate,

    @field:NotNull(message = "Invalid input")
    @field:Min(value = 1)
    @field:Max(value = 48)
    val numberOfInstallments: Int,

    @field:NotNull(message = "Invalid input")
    @field:Min(value = 1)
    val customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFistOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
