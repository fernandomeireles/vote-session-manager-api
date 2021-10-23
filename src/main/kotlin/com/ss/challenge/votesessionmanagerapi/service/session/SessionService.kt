package com.ss.challenge.votesessionmanagerapi.service.session

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.repository.session.SessionRepository
import com.ss.challenge.votesessionmanagerapi.service.session.converter.SessionConverter
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotClosedException
import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.service.subject.converter.SubjectConverter
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
    private val sessionConverter: SessionConverter,
    private val subjectService: ISubjectService,
    private val subjectConverter: SubjectConverter
) : ISessionService {
    override fun create(
        subjectId: Long,
        duration: Long
    ): SessionDto {

        val subjectEntity = subjectConverter.toEntity(
            subjectService.find(subjectId)
        )
        return sessionConverter.toDto(
            sessionRepository.create(
                subjectEntity,
                duration,
                LocalDateTime.now().plusMinutes(duration)
            )
        )
    }

    override fun findById(sessionId: Long): SessionDto {
        return sessionConverter.toDto(sessionRepository.findById(sessionId))
    }

    override fun findAll(): List<SessionDto> {
        return sessionConverter.listToDto(sessionRepository.findAll())
    }

    override fun inactiveSession(sessionId: Long, referenceDate: LocalDateTime): SessionDto {
        val session = sessionRepository.findById(sessionId)

        if (isValidDate(session.dateEnd, referenceDate)) {
            throw SessionNotClosedException(sessionId)
        }

        session.isActive = false
        return sessionConverter.toDto(sessionRepository.update(session))
    }

    override fun validActiveSession(sessionId: Long, referenceDate: LocalDateTime): Boolean {
        val session = sessionRepository.findById(sessionId)
        return isValidDate(session.dateEnd, referenceDate) && session.isActive
    }

    private fun isValidDate(endDate: LocalDateTime, referenceDate: LocalDateTime): Boolean {
        return endDate.isAfter(referenceDate) || endDate == referenceDate
    }
}
