package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session

import com.ss.challenge.votesessionmanagerapi.service.session.ISessionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["vote-session-manager"])
@RestController
@Validated
@RequestMapping("api/v1/session")
class SessionEndpoint(private val iSessionService: ISessionService) {

    @ApiOperation(value = "Creates a session for the voted subject")
    @PostMapping("/create")
    fun create(
        @NotNull
        @RequestHeader subjectId: Long,
        @DefaultValue("1L")
        @RequestHeader durationInMinutes: Long
    ): SessionDto {
        return iSessionService.create(subjectId, durationInMinutes)
    }

    @ApiOperation(value = "Get a session by Id")
    @GetMapping("{idSession}")
    fun getSession(@NotNull @PathVariable idSession: Long): SessionDto? {
        return iSessionService.findById(idSession)
    }

    @ApiOperation(value = "Get all sessions")
    @GetMapping("/getAllSessions")
    fun getAllSessions(): List<SessionDto>? {
        return iSessionService.findAll()
    }

    @ApiOperation(value = "Close session")
    @PutMapping("/closeSession/{idSession}")
    fun putCloseSession(@NotNull @PathVariable idSession: Long): SessionDto? {
        return iSessionService.inactiveSession(idSession)
    }
}
