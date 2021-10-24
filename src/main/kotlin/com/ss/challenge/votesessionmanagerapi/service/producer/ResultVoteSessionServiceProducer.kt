package com.ss.challenge.votesessionmanagerapi.service.producer

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.queue.resultVoteSession.ResultVoteSessionProducer
import org.springframework.stereotype.Service
import vote.result.ResultVoteSession

@Service
class ResultVoteSessionServiceProducer(private val resultVoteSessionProducer: ResultVoteSessionProducer) {

    fun post(resultVoteSessionEntity: ResultVoteSessionEntity) {

        resultVoteSessionProducer.post(
            ResultVoteSession(
                resultVoteSessionEntity.sessionEntity.id,
                resultVoteSessionEntity.sessionEntity.subjectEntity.subject,
                resultVoteSessionEntity.negativeVote,
                resultVoteSessionEntity.positiveVote,
                resultVoteSessionEntity.status,
                resultVoteSessionEntity.dateUpdate.toLocalDate().toString()
            )
        )
    }
}
