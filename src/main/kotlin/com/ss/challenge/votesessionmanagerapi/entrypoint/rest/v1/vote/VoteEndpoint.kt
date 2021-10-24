package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import com.ss.challenge.votesessionmanagerapi.service.vote.IVoteService
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration


@Api(tags = ["Vote"])
@RestController
@Validated
@RequestMapping("api/v1/vote")
class VoteEndpoint(private val iVoteService: IVoteService) {

    @ApiOperation(value = "Creates a new vote in session")
    @PostMapping("/create")
    fun create(
        @NotNull
        @RequestHeader sessionId: Long,
        @NotNull
        @RequestHeader userId: Long,
        @NotNull
        @RequestHeader userApprovedSession: Boolean,
    ): VoteDto {
        return iVoteService.create(sessionId, userId, userApprovedSession)
    }
}
