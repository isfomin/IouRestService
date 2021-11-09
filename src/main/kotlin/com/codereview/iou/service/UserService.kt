package com.codereview.iou.service

import com.codereview.iou.model.converter.UserConverter
import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.model.dto.UsersDto
import com.codereview.iou.model.entity.User
import com.codereview.iou.repository.UserRepository
import javassist.NotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    val converter: UserConverter = Mappers.getMapper(UserConverter::class.java)

    @Autowired
    lateinit var repository: UserRepository

    fun addUser(userDto: UserDto): User {
        val user = converter.convertToEntity(userDto)
        if (validateUserEntity(user)) {
            return repository.save(user)
        } else {
            throw IllegalArgumentException("Entity {$user} not valid.")
        }
    }

    fun getUser(userId: Long): UserDto {
        val user = repository.findById(userId).get()
        return converter.convertToDto(user)
    }

    fun deleteUser(userDto: UserDto) {
        if (userDto.id != null) {
            repository.delete(converter.convertToEntity(userDto))
        } else {
            throw NotFoundException("User #$userDto not found.")
        }
    }

    fun listUsers(): UsersDto {
        val items = repository.findAll()
        return UsersDto(items.map {
            converter.convertToDto(it)
        })
    }

    fun validateUserEntity(user: User?): Boolean {
        return user != null && (user.name?.isNotEmpty() ?: false)
    }
}