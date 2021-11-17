package com.codereview.iou.model.validation

import com.codereview.iou.model.entity.User

fun validateUserNameEntity(user: User?): Boolean {
    return user != null && (user.name?.isNotEmpty() ?: false)
}