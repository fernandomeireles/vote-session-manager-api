package com.ss.challenge.votesessionmanagerapi.service.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultStatusEnum
import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.repository.resultVoteSession.ResultVoteSessionRepository
import com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.exception.NoResultVotesInSessionException
import com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.vo.ResultVoteSessionVo
import com.ss.challenge.votesessionmanagerapi.service.session.ISessionService
import com.ss.challenge.votesessionmanagerapi.service.session.converter.SessionConverter
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotClosedException
import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.vote.IVoteService
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ResultVoteSessionService(
    private val resultVoteSessionRepository: ResultVoteSessionRepository,
    private val iSessionService: ISessionService,
    private val sessionConverter: SessionConverter,
    private val iSubjectService: ISubjectService,
    private val voteService: IVoteService
) :
    IResultVoteSession {
    override fun getResultVoteSession(sessionId: Long): ResultVoteSessionEntity {
        val session = validSession(sessionId)

        val resultSession = resultVoteSessionRepository.findResultBySessionEntity(session)

        if (resultSession != null) {
            return resultSession
        }

        val resultCalculate = calculateResultSession(session)

        return resultVoteSessionRepository.create(
            session,
            resultCalculate.resultPositivesVotes,
            resultCalculate.resultNegativesVotes,
            resultCalculate.resultStatus
        )
    }

    private fun validSession(sessionId: Long): SessionEntity {

        if (iSessionService.validActiveSession(sessionId, LocalDateTime.now())) {
            throw SessionNotClosedException(sessionId)
        }
        val sessionDto = iSessionService.findById(sessionId)
        val subjectDto =
            iSubjectService.find(sessionDto.subjectId ?: throw SubjectNotFoundException(0L))

        return sessionConverter.toEntity(sessionDto, subjectDto)
    }

    private fun calculateResultSession(sessionEntity: SessionEntity): ResultVoteSessionVo {
        val votesBySession =
            voteService.findVoteBySessionEntity(sessionEntity.id ?: throw VoteNotFoundException(0L))

        if (votesBySession.isEmpty()) {
            throw NoResultVotesInSessionException(
                sessionEntity.id ?: throw VoteNotFoundException(0L)
            )
        }
        var positiveVotes = 0L
        var negativeVotes = 0L

        votesBySession.forEach {
            if (it.isApprovedSession) {
                positiveVotes++
            } else {
                negativeVotes++
            }
        }

        var result = if (positiveVotes >= negativeVotes) ResultStatusEnum.APPROVED_SESSION
        else ResultStatusEnum.NOT_APPROVED_SESSION

        return ResultVoteSessionVo(negativeVotes, positiveVotes, result)
    }
}
