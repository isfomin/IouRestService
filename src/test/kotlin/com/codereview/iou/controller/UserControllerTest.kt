package com.codereview.iou.controller

import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.model.dto.UsersDto
import com.codereview.iou.model.entity.User
import com.codereview.iou.service.PromissoryNoteService
import com.codereview.iou.service.UserService
import com.codereview.iou.util.*
import javassist.NotFoundException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var promissoryNoteService: PromissoryNoteService

    @Test
    @DisplayName("when occur a request `/api/v1/user` with the get method, a json should be returned")
    fun testGet() {
        val userId = genLongId()
        val username = genName()

        Mockito.`when`(userService.listUsers()).thenReturn(
            UsersDto(
                listOf(UserDto(id = userId, name = username))
            )
        )

        mvc.perform(get("/api/v1/user"))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.users[0].id").value(userId))
            .andExpect(jsonPath("$.users[0].name").value(username))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/user` with the post method, a json should be returned")
    fun testPost() {
        val userId = genLongId()
        val username = genName()
        val userDto = UserDto(name = username)

        Mockito.`when`(userService.addUser(userDto)).thenReturn(
            User(userId, username)
        )

        mvc.perform(postJson("/api/v1/user", userDto))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo {
                println(it.response.contentAsString)
            }
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.name").value(username))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/user` with the delete method, a json should be returned")
    fun testDelete() {
        val userDto = UserDto(id = genLongId())
        val message = "not found"

        mvc.perform(deleteJson("/api/v1/user", userDto))
            .jsonExpect()
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value(message))
    }

    @Test
    @DisplayName("when occur a request `/api/v1/user` with the delete method and id is null, error should be returned")
    fun testDeleteUnsuccessful() {
        val userDtoWithNull = UserDto(id = null)
        val messageError = "not found"

        given(userService.deleteUser(userDtoWithNull)).willAnswer {
            throw NotFoundException(messageError)
        }

        mvc.perform(deleteJson("/api/v1/user", userDtoWithNull))
            .jsonExpect()
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value(messageError))
    }
}