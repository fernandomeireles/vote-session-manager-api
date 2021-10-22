package com.ss.challenge.votesessionmanagerapi.repository.vote

import com.ss.challenge.votesessionmanagerapi.entity.vote.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IVoteRepository : JpaRepository<VoteEntity, Long>
