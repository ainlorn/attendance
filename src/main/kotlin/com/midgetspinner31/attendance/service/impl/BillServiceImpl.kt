package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.config.BillProperties
import com.midgetspinner31.attendance.db.dao.BillRepository
import com.midgetspinner31.attendance.db.dao.TransactionRepository
import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.*
import com.midgetspinner31.attendance.dto.BillDto
import com.midgetspinner31.attendance.enumerable.BillStatus
import com.midgetspinner31.attendance.enumerable.TransactionType
import com.midgetspinner31.attendance.exception.AccessDeniedException
import com.midgetspinner31.attendance.exception.BillNotFoundException
import com.midgetspinner31.attendance.exception.BillStatusLockedException
import com.midgetspinner31.attendance.mapper.BillMapper
import com.midgetspinner31.attendance.service.BillService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class BillServiceImpl(
    private val billRepository: BillRepository,
    private val transactionRepository: TransactionRepository,
    private val billProperties: BillProperties,
    private val billMapper: BillMapper,
    private val userRepository: UserRepository
) : BillService {

    @Transactional
    override fun uploadBill(amount: Long, file: MultipartFile): BillDto {
        val user = userRepository.getCurrentUser() as Student
        val filename = UUID.randomUUID().toString()
        file.transferTo(File(billProperties.savePath, filename))

        val bill = billRepository.save(Bill().apply {
            this.filename = filename
            this.contentType = file.contentType
            this.student = user
            this.status = BillStatus.PENDING
            this.amount = amount
        })

        return billMapper.toDto(bill)
    }

    override fun getBill(id: UUID): BillDto {
        val user = userRepository.getCurrentUser()!!
        val bill = billRepository.findByIdOrNull(id) ?: throw BillNotFoundException()

        if (user is Student && user.id == bill.student!!.id || user is Admin || user is Trainer) {
            return billMapper.toDto(bill)
        } else {
            throw AccessDeniedException()
        }
    }

    override fun getPendingBills(): List<BillDto> {
        return billRepository.findAllByStatus(BillStatus.PENDING).map { billMapper.toDto(it) }
    }

    @Transactional
    override fun approveBill(id: UUID): BillDto {
        val bill = billRepository.findByIdOrNull(id) ?: throw BillNotFoundException()
        if (bill.status != BillStatus.PENDING) {
            throw BillStatusLockedException()
        }

        bill.status = BillStatus.VERIFIED
        transactionRepository.save(Transaction().apply {
            this.student = bill.student
            this.transactionType = TransactionType.DEPOSIT
            this.key = bill.id
            this.sum = bill.amount
            this.dt = bill.createdDt
        })
        return billMapper.toDto(bill)
    }

    @Transactional
    override fun declineBill(id: UUID): BillDto {
        val bill = billRepository.findByIdOrNull(id) ?: throw BillNotFoundException()
        if (bill.status != BillStatus.PENDING) {
            throw BillStatusLockedException()
        }

        bill.status = BillStatus.DECLINED
        return billMapper.toDto(bill)
    }

    @Transactional
    override fun deleteBill(id: UUID) {
        val user = userRepository.getCurrentUser()!!
        val bill = billRepository.findByIdOrNull(id) ?: throw BillNotFoundException()

        if (user.id != bill.student!!.id) {
            throw AccessDeniedException()
        }

        if (bill.status != BillStatus.PENDING) {
            throw BillStatusLockedException()
        }

        billRepository.delete(bill)
    }
}
