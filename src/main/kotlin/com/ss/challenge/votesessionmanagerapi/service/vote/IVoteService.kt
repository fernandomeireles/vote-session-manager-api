package com.ss.challenge.votesessionmanagerapi.service.vote

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote.VoteDto

interface IVoteService {

    fun create(
        sessionId: Long,
        userId: Long,
        isAprovedSession: Boolean
    ): VoteDto

    fun findVoteBySessionEntity(sessionId: Long): List<VoteDto>
}
