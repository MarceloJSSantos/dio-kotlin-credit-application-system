package mjss.dio.creditapplicationsystem.repository

import mjss.dio.creditapplicationsystem.entity.Address
import mjss.dio.creditapplicationsystem.entity.Credit
import mjss.dio.creditapplicationsystem.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTestInt {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach
    fun setup(){
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by code`(){
        //given
        val creditCode1 = UUID.fromString("78553868-8434-4852-b829-20f538702dd8")
        val creditCode2 = UUID.fromString("edc55fc8-f60d-43c6-b613-55d59ba025b5")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        //when
        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!
        val fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2)!!

        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)
    }

    @Test
    fun `should find all credits by customer id`() {
        //given
        val customerId: Long = 1L
        //when
        val creditList: List<Credit> = creditRepository.findAllByCustomerId(customerId)
        //then
        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList.size).isEqualTo(2)
        Assertions.assertThat(creditList).contains(credit1, credit2)
    }
    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFistInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 24),
        numberOfInstallments: Int = 5,
        customer: Customer
    ) = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFistInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    private fun buildCustomer(
        firstName: String = "teste",
        lastName: String = "unitário",
        cpf: String = "57798313001",
        email: String = "teste.unitario@email.com",
        password: String = "1234",
        zipCode: String = "21000500",
        street: String = "Rua do teste",
        income: BigDecimal = BigDecimal.valueOf(1000.0)
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street
        ),
        income = income
    )
}