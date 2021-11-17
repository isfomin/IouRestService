package com.codereview.iou.controller.handler

import com.codereview.iou.model.dto.ExceptionDto
import com.codereview.iou.util.exeption.ValidateException
import javassist.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ValidateException::class, IllegalArgumentException::class, Exception::class)
    fun handleIllegalArgumentException (ex: Exception, request: WebRequest): ResponseEntity<ExceptionDto> {
        return ResponseEntity<ExceptionDto>(
            ExceptionDto(ex.message ?: ex.toString()), HttpHeaders(), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class, NoSuchElementException::class)
    fun handleNotFoundException (ex: Exception, request: WebRequest): ResponseEntity<ExceptionDto> {
        return ResponseEntity<ExceptionDto>(
            ExceptionDto(ex.message ?: ex.toString()), HttpHeaders(), HttpStatus.NOT_FOUND
        )
    }
}