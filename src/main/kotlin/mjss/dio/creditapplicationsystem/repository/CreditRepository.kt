package mjss.dio.creditapplicationsystem.repository

import mjss.dio.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRepository:JpaRepository<Credit, Long>