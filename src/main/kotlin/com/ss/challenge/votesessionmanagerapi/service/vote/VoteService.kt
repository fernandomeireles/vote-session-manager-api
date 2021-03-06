package com.ss.challenge.votesessionmanagerapi.service.vote

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote.VoteDto
import com.ss.challenge.votesessionmanagerapi.repository.vote.VoteRepository
import com.ss.challenge.votesessionmanagerapi.service.session.ISessionService
import com.ss.challenge.votesessionmanagerapi.service.session.converter.SessionConverter
import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.user.IUserService
import com.ss.challenge.votesessionmanagerapi.service.user.converter.UserConverter
import com.ss.challenge.votesessionmanagerapi.service.vote.converter.VoteConverter
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteCapacityExceededException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteCpfCapacityExceededException
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteInvalidException
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Bucket4j
import io.github.bucket4j.Refill
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class VoteService(
    private val voteRepository: VoteRepository,
    private val voteConverter: VoteConverter,
    private val sessionService: ISessionService,
    private val userService: IUserService,
    private val userConverter: UserConverter,
    private val sessionConverter: SessionConverter,
    private val subjectService: ISubjectService
) : IVoteService {

    override fun create(
        sessionId: Long,
        userId: Long,
        isAprovedSession: Boolean
    ): VoteDto {

        if (!BUCKET.tryConsume(1L)) {
            throw VoteCapacityExceededException(sessionId, userId, isAprovedSession)
        }

        val user = userConverter.toEntity(
            userService.findById(userId)
        )

        val session = loadSession(sessionId)

        isValidVote(sessionId, user)

        return voteConverter.toDto(
            voteRepository.create(
                session,
                user,
                true,
                isAprovedSession
            )
        )
    }

    override fun findVoteBySessionEntity(sessionId: Long): List<VoteDto> {
        return voteConverter.listToDto(
            voteRepository.findVoteBySessionEntity(
                loadSession(sessionId)
            )
        )
    }

    override fun createWithCpf(
        sessionId: Long,
        userCpf: String,
        isAprovedSession: Boolean
    ): VoteDto {

        if (!BUCKET.tryConsume(1L)) {
            throw VoteCpfCapacityExceededException(sessionId, userCpf, isAprovedSession)
        }

        val user = userConverter.toEntity(
            userService.findByCpf(userCpf)
        )

        val session = loadSession(sessionId)

        isValidVote(sessionId, user)

        return voteConverter.toDto(
            voteRepository.create(
                session,
                user,
                true,
                isAprovedSession
            )
        )
    }

    private fun isValidVote(sessionId: Long, user: UserEntity) {
        isValidSession(sessionId)
        isValidUser(user)
        isNotDuplicatedVote(sessionId, user)
    }

    private fun isValidSession(sessionId: Long) {
        if (!sessionService.validActiveSession(sessionId, LocalDateTime.now())) {
            throw VoteInvalidException("Session is closed")
        }
    }

    private fun isValidUser(user: UserEntity) {
        if (!user.isActive) {
            throw VoteInvalidException("User is inactive")
        }
    }

    private fun isNotDuplicatedVote(sessionId: Long, user: UserEntity) {

        val userVotes = voteRepository.findVoteByUser(user) ?: return

        if (userVotes.isEmpty()) {
            return
        }

        if (userVotes.stream().anyMatch {
            it.sessionEntity.id == sessionId
        }
        ) {
            throw VoteInvalidException("User duplicated vote")
        }
    }

    private fun loadSession(sessionId: Long): SessionEntity {
        val sessionDto = sessionService.findById(sessionId)
        val subjectDto = subjectService.find(
            sessionDto.subjectId ?: throw SubjectNotFoundException(0L)
        )
        return sessionConverter.toEntity(sessionDto, subjectDto)
    }

    companion object {
        var BUCKET: Bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(200000, Refill.greedy(200000, Duration.ofMinutes(1))))
            .build()
    }
}
