package mjss.dio.creditapplicationsystem.repository

import mjss.dio.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository:JpaRepository<Customer, Long>