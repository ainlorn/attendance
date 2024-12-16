package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.config.BillProperties
import com.midgetspinner31.attendance.dto.BillDto
import com.midgetspinner31.attendance.service.BillService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@ApiV1
class BillController(
    private val billService: BillService,
    private val billProperties: BillProperties
) {
    @PostMapping("/bills", consumes = ["multipart/form-data"])
    fun uploadBill(
        @RequestParam file: MultipartFile,
        @RequestParam amount: Long
    ): ItemResponse<BillDto> {
        return ItemResponse(billService.uploadBill(amount, file))
    }

    @GetMapping("/bills/pending")
    fun getPendingBills(): ListResponse<BillDto> {
        return ListResponse(billService.getPendingBills())
    }

    @GetMapping("/me/bills")
    fun getMyBills(): ListResponse<BillDto> {
        return ListResponse(billService.getMyBills())
    }

    @GetMapping("/bills/by-id/{id}")
    fun getBill(@PathVariable id: UUID): ItemResponse<BillDto> {
        return ItemResponse(billService.getBill(id))
    }

    @GetMapping("/bills/by-id/{id}/img")
    fun getBillImg(@PathVariable id: UUID): ResponseEntity<Resource> {
        val bill = billService.getBill(id)
        return ResponseEntity
            .ok()
            .contentType(MediaType.valueOf(bill.contentType!!))
            .body(FileSystemResource(File(billProperties.savePath, bill.filename!!)))
    }

    @DeleteMapping("/bills/by-id/{id}")
    fun deleteBill(@PathVariable id: UUID) {
        billService.deleteBill(id)
    }

    @PostMapping("/bills/by-id/{id}/approve")
    fun approveBill(@PathVariable id: UUID): ItemResponse<BillDto> {
        return ItemResponse(billService.approveBill(id))
    }

    @PostMapping("/bills/by-id/{id}/decline")
    fun declineBill(@PathVariable id: UUID): ItemResponse<BillDto> {
        return ItemResponse(billService.declineBill(id))
    }
}
