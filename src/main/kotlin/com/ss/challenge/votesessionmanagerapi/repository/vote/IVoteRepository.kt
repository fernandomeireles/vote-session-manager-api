package com.ss.challenge.votesessionmanagerapi.repository.vote

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.entity.vote.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IVoteRepository : JpaRepository<VoteEntity, Long> {

    fun findBySessionEntity(sessionEntity: SessionEntity): List<VoteEntity>

    fun findByUserEntity(userEntity: UserEntity): List<VoteEntity>
}
