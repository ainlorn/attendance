package com.midgetspinner31.attendance.mapper

import com.midgetspinner31.attendance.config.BillProperties
import com.midgetspinner31.attendance.db.entity.Bill
import com.midgetspinner31.attendance.dto.BillDto
import org.springframework.stereotype.Component

@Component
class BillMapper(
    private val billProperties: BillProperties
) {
    fun toDto(bill: Bill): BillDto {
        return BillDto(
            bill.id,
            bill.filename,
            bill.contentType,
            billProperties.url!!.format(bill.id.toString()),
            bill.student?.toPublicDto(),
            bill.status,
            bill.amount,
            bill.createdDt
        )
    }
}
