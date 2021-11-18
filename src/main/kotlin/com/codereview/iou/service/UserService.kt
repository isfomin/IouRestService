package com.codereview.iou.service

import com.codereview.iou.model.converter.UserConverter
import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.model.dto.UsersDto
import com.codereview.iou.model.entity.User
import com.codereview.iou.model.validation.validateUserNameEntity
import com.codereview.iou.repository.UserRepository
import com.codereview.iou.util.validate
import javassist.NotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    var repository: UserRepository
) {

    val converter: UserConverter = Mappers.getMapper(UserConverter::class.java)

    fun addUser(userDto: UserDto): User {
        val user = converter.convertToEntity(userDto)

        user.validate(
            "Username required in {$user} entity.",
            ::validateUserNameEntity
        ).run {
            return repository.save(this)
        }
    }

    fun getUser(userId: Long): UserDto {
        return repository.findById(userId).get().run {
            converter.convertToDto(this)
        }
    }

    fun deleteUser(userDto: UserDto): Int {
        if (userDto.id != null) {
            return if (repository.existsById(userDto.id!!)) {
                repository.delete(converter.convertToEntity(userDto))
                1
            } else {
                0
            }
        } else {
            throw NotFoundException("User {$userDto} not found.")
        }
    }

    fun listUsers(): UsersDto {
        val items = repository.findAll()
        return UsersDto(items.map {
            converter.convertToDto(it)
        })
    }
}