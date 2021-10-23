package com.ss.challenge.votesessionmanagerapi.service.session

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import java.time.LocalDateTime

interface ISessionService {

    fun create(
        subjectId: Long,
        duration: Long
    ): SessionDto

    fun findById(sessionId: Long): SessionDto

    fun findAll(): List<SessionDto>

    fun inactiveSession(sessionId: Long, referenceDate: LocalDateTime): SessionDto

    fun validActiveSession(sessionId: Long, referenceDate: LocalDateTime): Boolean
}
