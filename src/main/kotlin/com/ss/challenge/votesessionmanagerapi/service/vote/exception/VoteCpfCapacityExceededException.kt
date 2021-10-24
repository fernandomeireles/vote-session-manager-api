package com.ss.challenge.votesessionmanagerapi.service.vote.exception

data class VoteCpfCapacityExceededException(
    private val sessionId: Long,
    private val cpf: String,
    private val isAprovedSession: Boolean
) :
    RuntimeException(
        "Vote Limit por minute exceeded. Request -> sessionID=$sessionId, " +
                "cpf=$cpf, isAprovedSession$isAprovedSession"
    )
