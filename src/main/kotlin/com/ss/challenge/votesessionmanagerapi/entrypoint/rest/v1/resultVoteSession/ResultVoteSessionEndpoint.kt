package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity
import com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.IResultVoteSession
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Result-Vote-Session"])
@RestController
@Validated
@RequestMapping("api/v1/result")
class ResultVoteSessionEndpoint(private val iResultVoteSession: IResultVoteSession) {

    @ApiOperation(value = "Get a result vote session")
    @GetMapping("/getResultVoteSession")
    fun getResultVoteSession(
        @NotNull
        @RequestHeader sessionId: Long
    ): ResultVoteSessionEntity {
        return iResultVoteSession.getResultVoteSession(sessionId)
    }
}
