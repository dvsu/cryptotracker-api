package com.david.cryptotracker.controllers

import com.david.cryptotracker.models.CustomError
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import javax.persistence.EntityNotFoundException
import javax.persistence.NoResultException
import javax.validation.ConstraintViolationException


@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(
        ConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class
    )
    fun constraintViolationException(e: Exception): ResponseEntity<CustomError> {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Bad request")
    }

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class
    )
    fun notFoundException(e: Exception): ResponseEntity<CustomError> {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Resource not found")
    }

    @ExceptionHandler(
        Exception::class
    )
    fun internalServerErrorException(e: Exception): ResponseEntity<CustomError> {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Request body may be missing/ mismatched")
    }

    private fun generateErrorResponse(
        status: HttpStatus,
        message: String,
    ): ResponseEntity<CustomError> {
        return ResponseEntity(CustomError(status, message), status)
    }

}