package com.codereview.iou.model.converter

import com.codereview.iou.model.dto.UserDto
import com.codereview.iou.model.entity.User
import org.mapstruct.Mapper

@Mapper
interface UserConverter {

    fun convertToEntity(dto: UserDto): User

    fun convertToDto(user: User): UserDto
}