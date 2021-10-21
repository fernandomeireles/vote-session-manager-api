package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import io.swagger.annotations.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["vote-session-manager"])
@RestController
@Validated
@RequestMapping("api/v1/vote")
class VoteEndpoint
