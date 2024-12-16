package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Bill
import com.midgetspinner31.attendance.enumerable.BillStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BillRepository : JpaRepository<Bill, UUID> {

    fun findAllByStatus(status: BillStatus): List<Bill>

    fun findAllByStudentId(studentId: UUID): List<Bill>

}
