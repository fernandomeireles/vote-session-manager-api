package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import java.time.LocalDateTime

data class VoteDto(
    val userId: Long,
    val vote: Boolean,
    val dateVote: LocalDateTime,
    val voteId: Long
)
