package com.ss.challenge.votesessionmanagerapi.service.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity

interface IResultVoteSession {
    fun getResultVoteSession(sessionId: Long): ResultVoteSessionEntity
}
