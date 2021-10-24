package com.ss.challenge.votesessionmanagerapi.service.vote.exception

data class VoteCapacityExceededException(
    private val sessionId: Long,
    private val userId: Long,
    private val isAprovedSession: Boolean
) :
    RuntimeException(
        "Vote Limit por minute exceeded. Request -> sessionID=$sessionId, " +
            "userID=$userId, isAprovedSession$isAprovedSession"
    )
