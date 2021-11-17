package com.codereview.iou.service

import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.util.exeption.ValidateException
import javassist.NotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
internal class UserServiceTest {

    @Autowired
    lateinit var service: UserService

    @Test
    @DisplayName("when a service creates a user, the entity should be returned and equal to the data transfer object")
    fun addUser() {
        val userName = "Bobby"
        val userDto1 = UserDto().apply { name = userName }
        val user = service.addUser(userDto1)
        val userDto = service.getUser(user.id!!)

        assertEquals(userName, userDto.name)
    }

    @Test
    @DisplayName("when user dto is empty, an error should be returned")
    fun addEmptyUser() {
        val userName = ""
        val userDto1 = UserDto().apply { name = userName }

        assertThrows(ValidateException::class.java) {
            service.addUser(userDto1)
        }
    }

    @Test
    @DisplayName("when user id is invalid, an error should be returned")
    fun getNotExistUser() {
        assertThrows(NoSuchElementException::class.java) {
            service.getUser(-1L)
        }
    }

    @Test
    @DisplayName("when user dto is empty, an error should be returned")
    fun deleteEmptyUser() {
        assertThrows(NotFoundException::class.java) {
            service.deleteUser(UserDto())
        }
    }

    @Test
    @DisplayName("when a service get list of users, the data transfer object should be returned")
    fun listUsers() {
        val userName1 = "Bobby"
        val userName2 = "Timofei"
        val userDto1 = UserDto().apply { name = userName1 }
        val userDto2 = UserDto().apply { name = userName2 }
        val user1 = service.addUser(userDto1)
        val user2 = service.addUser(userDto2)
        val listUsers = service.listUsers()

        assertEquals(userName1, listUsers.users.find { it.id == user1.id }!!.name)
        assertEquals(userName2, listUsers.users.find { it.id == user2.id }!!.name)
    }
}