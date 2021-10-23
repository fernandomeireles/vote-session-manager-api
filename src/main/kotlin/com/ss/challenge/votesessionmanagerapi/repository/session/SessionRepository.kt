package com.ss.challenge.votesessionmanagerapi.repository.session

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotFoundException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SessionRepository(private val iSessionRepository: ISessionRepository) {

    fun create(
        subjectEntity: SubjectEntity,
        duration: Long,
        dateEnd: LocalDateTime
    ): SessionEntity {
        return iSessionRepository.save(
            SessionEntity(
                null,
                subjectEntity,
                duration,
                dateEnd,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            )
        )
    }

    fun findById(sessionId: Long): SessionEntity {
        return iSessionRepository.findById(sessionId).orElseThrow {
            throw SessionNotFoundException(sessionId)
        }
    }

    fun update(sessionEntity: SessionEntity): SessionEntity {
        return iSessionRepository.save(sessionEntity)
    }

    fun findAll(): List<SessionEntity> {
        return iSessionRepository.findAll()
    }
}
