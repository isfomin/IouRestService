package com.codereview.iou.util

import com.codereview.iou.util.exeption.ValidateException

inline fun <T> T.validate(validateErrorMessage: String = "Validation error", block: (T) -> Boolean): T {
    if (!block(this)) throw ValidateException(validateErrorMessage)
    return this
}