package mjss.dio.creditapplicationsystem.service.impl

import mjss.dio.creditapplicationsystem.entity.Customer
import mjss.dio.creditapplicationsystem.exception.BusinessException
import mjss.dio.creditapplicationsystem.repository.CustomerRepository
import mjss.dio.creditapplicationsystem.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
): ICustomerService{
    override fun save(customer: Customer): Customer =
        this.customerRepository.save(customer)

    override fun findById(customerId: Long): Customer =
        this.customerRepository.findById(customerId).orElseThrow {
            throw BusinessException("Id $customerId not found!")
        }

    override fun delete(customerId: Long) {
        val customer: Customer = this.findById(customerId)
        this.customerRepository.delete(customer)
    }
}