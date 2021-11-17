package com.codereview.iou.controller

import com.codereview.iou.model.dto.MessageDto
import com.codereview.iou.model.dto.RemovedMessageDto
import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.model.dto.UsersDto
import com.codereview.iou.model.entity.User
import com.codereview.iou.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @Autowired
    lateinit var service: UserService

    @GetMapping()
    fun getAllUsers(): UsersDto {
        return service.listUsers()
    }

    @PostMapping
    fun addUser(@RequestBody userDto: UserDto): User {
        return service.addUser(userDto)
    }

    @PutMapping
    fun editIou(): ResponseEntity<MessageDto> {
        return ResponseEntity(MessageDto("method not supported"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody userDto: UserDto): ResponseEntity<RemovedMessageDto> {
        val countRemoved = service.deleteUser(userDto)
        return ResponseEntity(RemovedMessageDto(if (countRemoved > 0) "success" else "not found", countRemoved), HttpStatus.OK)
    }
}