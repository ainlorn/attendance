package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.BillDto
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface BillService {

    @PreAuthorize("@userService.isStudent()")
    fun uploadBill(amount: Long, file: MultipartFile): BillDto

    @PreAuthorize("isAuthenticated()")
    fun getBill(id: UUID): BillDto

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun getPendingBills(): List<BillDto>

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun approveBill(id: UUID): BillDto

    @PreAuthorize("@userService.isTrainer() || @userService.isAdmin()")
    fun declineBill(id: UUID): BillDto

    @PreAuthorize("@userService.isStudent()")
    fun deleteBill(id: UUID)

}
