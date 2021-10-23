package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import java.time.LocalDateTime

data class VoteDto(
    var idVote: Long?,
    var userId: Long?,
    var sessionId: Long?,
    var isValid: Boolean,
    var isApprovedSession: Boolean,
    var dateCreation: LocalDateTime,
    var dateUpdate: LocalDateTime
)
