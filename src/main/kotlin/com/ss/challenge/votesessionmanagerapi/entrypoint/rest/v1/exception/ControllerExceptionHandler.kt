package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.exception

import com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.exception.NoResultVotesInSessionException
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotClosedException
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.user.exception.CpfNotGeneratedException
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteCapacityExceededException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteCpfCapacityExceededException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteInvalidException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteNotFoundException
import org.apache.kafka.common.errors.AuthorizationException
import org.apache.tomcat.websocket.AuthenticationException
import org.hibernate.exception.ConstraintViolationException
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

@ControllerAdvice
class ControllerExceptionsHandler {

    @ExceptionHandler(
        ConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        IllegalArgumentException::class,
        SessionNotClosedException::class,
        VoteInvalidException::class,
        CpfNotGeneratedException::class
    )
    fun constraintViolationException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Bad request", e)
    }

    @ExceptionHandler(AuthorizationException::class)
    fun unauthorizedException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(
            HttpStatus.FORBIDDEN,
            "You are not authorized to do this operation",
            e
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun forbiddenException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(
            HttpStatus.UNAUTHORIZED,
            "You are not allowed to do this operation",
            e
        )
    }

    @ExceptionHandler(
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class,
        SubjectNotFoundException::class,
        UserNotFoundException::class,
        SessionNotFoundException::class,
        VoteNotFoundException::class,
        NoResultVotesInSessionException::class
    )

    fun notFoundException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e)
    }

    @ExceptionHandler(
        VoteCapacityExceededException::class,
        VoteCpfCapacityExceededException::class
    )
    fun toManyRequestException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(
            HttpStatus.TOO_MANY_REQUESTS,
            "Requests por minute exceeded, please wait 1 minute", e
        )
    }

    @ExceptionHandler(
        Exception::class
    )
    fun internalServerErrorException(e: Exception): ResponseEntity<ExceptionHandlerDto> {
        return generateErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Generic internal error", e
        )
    }

    private fun generateErrorResponse(
        status: HttpStatus,
        message: String,
        e: Exception
    ): ResponseEntity<ExceptionHandlerDto> {

        return ResponseEntity(
            ExceptionHandlerDto(
                status,
                "$message - $e.localizedMessage"
            ),
            status
        )
    }
}
