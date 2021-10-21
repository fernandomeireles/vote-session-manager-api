package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["vote-session-manager"])
@RestController
@Validated
@RequestMapping("api/v1/subject")
class SubjectEndpoint {

    @ApiOperation(value = "Creates a subject for the selected in vote session")
    @PostMapping("/create")
    fun create(
        @NotNull
        @RequestHeader subject: String
    ) {
        return
    }
}