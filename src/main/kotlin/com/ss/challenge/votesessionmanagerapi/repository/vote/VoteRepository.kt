package com.ss.challenge.votesessionmanagerapi.repository.vote

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.entity.vote.VoteEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VoteRepository(private val iVoteRepository: IVoteRepository) {

    fun create(
        sessionEntity: SessionEntity,
        userEntity: UserEntity,
        isValid: Boolean,
        isAprovedSession: Boolean
    ): VoteEntity {
        return iVoteRepository.save(
            VoteEntity(
                null,
                userEntity,
                sessionEntity,
                isValid,
                isAprovedSession,
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        )
    }

    fun findVoteBySessionEntity(sessionEntity: SessionEntity): List<VoteEntity> {
        return iVoteRepository.findBySessionEntity(sessionEntity)
    }

    fun findVoteByUser(userEntity: UserEntity): List<VoteEntity>? {
        return iVoteRepository.findByUserEntity(userEntity)
    }
}
