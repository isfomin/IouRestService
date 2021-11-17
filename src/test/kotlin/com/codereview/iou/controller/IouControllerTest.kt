package com.codereview.iou.controller

import com.codereview.iou.model.dto.*
import com.codereview.iou.model.entity.PromissoryNote
import com.codereview.iou.model.entity.User
import com.codereview.iou.service.PromissoryNoteService
import com.codereview.iou.service.UserService
import com.codereview.iou.util.*
import javassist.NotFoundException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest
@ActiveProfiles("test")
internal class IouControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var promissoryNoteService: PromissoryNoteService

    @MockBean
    lateinit var userService: UserService

    /**
     * Example struct json response:
     * {"notes":[{"id":null,"lender":{"id":null,"name":"Bobby"},"borrower":{"id":null,"name":"Timofei"},"amount":143.4}]}
     */
    @Test
    @DisplayName("when occur a request `/api/v1/iou` with the get method, a json should be returned")
    fun testGet() {
        val amount = genAmount()
        val name1 = genName()
        val name2 = genName()

        `when`(promissoryNoteService.listPromissoryNotes()).thenReturn(PromissoryNotesDto(
            listOf(PromissoryNoteDto(
                id = genLongId(),
                lender = User(genLongId(), name1),
                borrower = User(genLongId(), name2),
                amount = amount
            ))
        ))

        mvc.perform(get("/api/v1/iou"))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.notes[0].lender.name").value(name1))
            .andExpect(jsonPath("$.notes[0].borrower.name").value(name2))
            .andExpect(jsonPath("$.notes[0].amount").value(amount))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/iou/summed` with the get method, a json should be returned")
    fun testGetSummed() {
        val amount = genAmount()
        val name1 = genName()
        val name2 = genName()
        val userId1 = genLongId()
        val userId2 = genLongId()

        `when`(promissoryNoteService.listCalculatedSumNotes()).thenReturn(
            PromissoryNotesSummedDto(listOf(
                PromissoryNoteSummedDto(
                    lender = User(userId1, name1),
                    borrower = User(userId2, name2),
                    amount = amount
                ))
            )
        )

        mvc.perform(get("/api/v1/iou/summed"))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.notes[0].lender.name").value(name1))
            .andExpect(jsonPath("$.notes[0].borrower.name").value(name2))
            .andExpect(jsonPath("$.notes[0].amount").value(amount))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/iou` with the post method, a json should be returned")
    fun testPost() {
        val amount = genAmount()
        val userId1 = 1L
        val userId2 = 2L
        val name1 = genName()
        val name2 = genName()

        val promissoryNoteCreateDto = PromissoryNoteCreateDto(lender = userId1, borrower = userId2, amount = amount)

        `when`(promissoryNoteService.addPromissoryNote(promissoryNoteCreateDto)).thenReturn(
            PromissoryNote(
                id = genLongId(),
                lender = User(userId1, name1),
                borrower = User(userId2, name2),
                amount = amount)
        )

        mvc.perform(postJson("/api/v1/iou", promissoryNoteCreateDto))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.lender.name").value(name1))
            .andExpect(jsonPath("$.borrower.name").value(name2))
            .andExpect(jsonPath("$.amount").value(amount))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/iou` with the delete method, a json should be returned")
    fun testDelete() {
        val promissoryNoteDto = PromissoryNoteDto(id = genLongId())
        val message = "not found"

        mvc.perform(deleteJson("/api/v1/iou", promissoryNoteDto))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value(message))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/iou` with the delete method and id is null, error should be returned")
    fun testDeleteUnsuccessful() {
        val promissoryNoteDtoWithNull = PromissoryNoteDto(id = null)
        val messageError = "not found"

        given(promissoryNoteService.deletePromissoryNote(promissoryNoteDtoWithNull)).willAnswer {
            throw NotFoundException(
                messageError
            )
        }

        mvc.perform(deleteJson("/api/v1/iou", promissoryNoteDtoWithNull))
            .jsonExpect()
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value(messageError))
    }
}