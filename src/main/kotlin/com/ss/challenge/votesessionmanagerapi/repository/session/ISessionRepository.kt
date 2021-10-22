package com.ss.challenge.votesessionmanagerapi.repository.session

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ISessionRepository : JpaRepository<SessionEntity, Long>
