package mjss.dio.creditapplicationsystem.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import mjss.dio.creditapplicationsystem.entity.Address
import mjss.dio.creditapplicationsystem.entity.Customer
import mjss.dio.creditapplicationsystem.exception.BusinessException
import mjss.dio.creditapplicationsystem.repository.CustomerRepository
import mjss.dio.creditapplicationsystem.service.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.Optional
import java.util.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun `should create customer`(){
        //given (o que preciso)
        val fakeCustomer: Customer = buildCustomer()
        //sempre que eu
        every { customerRepository.save(any()) } returns fakeCustomer

        //when (método a ser testato
        val actual: Customer = customerService.save(fakeCustomer)

        //then (resultados do teste)
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify (exactly = 1){ customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `should find customer by id`(){
        //given (o que preciso)
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)

        //when (método a ser testato
        val actual: Customer = customerService.findById(fakeId)

        //then (resultados do teste)
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify (exactly = 1){ customerRepository.findById(fakeId) }
    }

    @Test
    fun `should not find by id and throw BusinessException`(){
        //given
        val fakeId: Long = Random().nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()

        //when

        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("Id $fakeId not found!")
        verify (exactly = 1){customerRepository.findById(fakeId)}
    }

    @Test
    fun `should delete customer by id`(){
        //given (o que preciso)
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer)} just runs

        //when (método a ser testato
        customerService.delete(fakeId)

        //then (resultados do teste)
        verify (exactly = 1){ customerRepository.findById(fakeId) }
        verify (exactly = 1){ customerRepository.delete(fakeCustomer) }
    }

    private fun buildCustomer(
        firstName: String = "teste",
        lastName: String = "unitário",
        cpf: String = "57798313001",
        email: String = "teste.unitario@email.com",
        password: String = "1234",
        zipCode: String = "21000500",
        street: String = "Rua do teste",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long = 1L
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
        income = income,
        id = id
    )
}