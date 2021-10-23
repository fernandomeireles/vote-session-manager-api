package com.ss.challenge.votesessionmanagerapi.repository.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultVoteSessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IResultVoteSessionRespository : JpaRepository<ResultVoteSessionEntity, Long> {

    fun findBySessionEntity(sessionEntity: SessionEntity): ResultVoteSessionEntity?
}
