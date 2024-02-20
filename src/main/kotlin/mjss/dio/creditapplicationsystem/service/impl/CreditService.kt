package mjss.dio.creditapplicationsystem.service.impl

import mjss.dio.creditapplicationsystem.entity.Credit
import mjss.dio.creditapplicationsystem.exception.BusinessException
import mjss.dio.creditapplicationsystem.repository.CreditRepository
import mjss.dio.creditapplicationsystem.service.ICreditService

import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
):ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }


    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (creditRepository.findByCreditCode(creditCode)
            ?: throw BusinessException("CreditCode $creditCode not found!"))
        return if (credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact Admin")
    }
}