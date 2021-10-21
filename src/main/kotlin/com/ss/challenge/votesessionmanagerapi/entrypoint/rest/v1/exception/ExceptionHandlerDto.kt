package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.Date

class ExceptionHandlerDto(
    status: HttpStatus,
    val message: String
) {

    val code: Int
    val status: String = status.name

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    val timestamp: Date = Date()

    init {
        code = status.value()
    }
}
