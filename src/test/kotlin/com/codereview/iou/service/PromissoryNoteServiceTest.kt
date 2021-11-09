package com.codereview.iou.service

import com.codereview.iou.model.dto.PromissoryNoteCreateDto
import com.codereview.iou.model.dto.PromissoryNoteDto
import com.codereview.iou.model.dto.UserDto
import javassist.NotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
internal class PromissoryNoteServiceTest {

    @Autowired
    lateinit var iouService: PromissoryNoteService

    @Autowired
    lateinit var userService: UserService

    @Test
    fun addPromissoryNote() {
        val userName1 = "Bobby"
        val userName2 = "Timofei"
        val userDto1 = UserDto().apply { name = userName1 }
        val userDto2 = UserDto().apply { name = userName2  }
        val lender = userService.addUser(userDto1)
        val borrower = userService.addUser(userDto2)
        val amount = 1000.00

        val promissoryNoteDto = PromissoryNoteCreateDto().apply {
            this.lender = lender.id
            this.borrower = borrower.id
            this.amount = amount
        }

        val promissoryNote = iouService.addPromissoryNote(promissoryNoteDto)

        assertEquals(userName1, promissoryNote.lender!!.name)
        assertEquals(userName2, promissoryNote.borrower!!.name)
        assertEquals(amount, promissoryNote.amount)
    }

    @Test
    fun getNotExistPromissoryNote() {
        assertThrows(NoSuchElementException::class.java) { iouService.getPromissoryNote(-1L) }
    }

    @Test
    fun deleteEmptyPromissoryNote() {
        assertThrows(NotFoundException::class.java) {
            iouService.deletePromissoryNote(PromissoryNoteDto())
        }
    }
}