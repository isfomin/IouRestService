package com.codereview.iou.service

import com.codereview.iou.model.dto.PromissoryNoteDto
import com.codereview.iou.model.dto.PromissoryNoteSummedDto
import com.codereview.iou.model.entity.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GroupingTest {

    @Test
    @DisplayName("when a several promissory notes with the same participants then summed list of promissory notes should be returned")
    fun groupingTest() {
        val user1 = User().apply { id = 0; name = "Bobby" }
        val user2 = User().apply { id = 1; name = "Timofei" }
        val user3 = User().apply { id = 2; name = "Jimmy" }
        val user4 = User().apply { id = 3; name = "Billy" }
        val iou1 = PromissoryNoteDto().apply {
            id = 0
            lender = user1
            borrower = user2
            amount = 11.50
        }
        val iou2 = PromissoryNoteDto().apply {
            id = 1
            lender = user1
            borrower = user2
            amount = 22.00
        }
        val iou3 = PromissoryNoteDto().apply {
            id = 2
            lender = user3
            borrower = user4
            amount = 22.00
        }
        val list = listOf(iou1, iou2, iou3)

        val grouping = list
            .groupingBy { "${it.lender?.id}-${it.borrower?.id}" }
            .reduce { _, accumulator, element ->
                PromissoryNoteDto().apply {
                    lender = accumulator.lender
                    borrower = accumulator.borrower
                    amount = accumulator.amount?.plus(element.amount!!)
                }
            }
            .map {
                PromissoryNoteSummedDto().apply {
                    lender = it.value.lender
                    borrower = it.value.borrower
                    amount = it.value.amount
                }
            }

        assertEquals(iou1.amount?.plus(iou2.amount!!), grouping[0].amount)
    }
}