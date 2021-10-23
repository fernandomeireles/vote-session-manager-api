package com.ss.challenge.votesessionmanagerapi.repository.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultStatusEnum
import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ResultVoteSessionRepository(private val iResultVoteSessionRespository: IResultVoteSessionRespository) {

    fun create(
        sessionEntity: SessionEntity,
        sessionPositiveVotes: Long,
        sessionNegativeVotes: Long,
        resultStatus: ResultStatusEnum
    ): ResultVoteSessionEntity {
        return iResultVoteSessionRespository.save(
            ResultVoteSessionEntity(
                null,
                sessionEntity,
                sessionPositiveVotes,
                sessionNegativeVotes,
                resultStatus.name,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            )
        )
    }

    fun findResultBySessionEntity(sessionEntity: SessionEntity): ResultVoteSessionEntity? {
        return iResultVoteSessionRespository.findBySessionEntity(sessionEntity)
    }
}
