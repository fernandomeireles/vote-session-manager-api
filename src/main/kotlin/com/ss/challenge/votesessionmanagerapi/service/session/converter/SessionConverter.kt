package com.ss.challenge.votesessionmanagerapi.service.session.converter

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotFoundException
import org.springframework.stereotype.Component

@Component
class SessionConverter {

    fun toDto(sessionEntity: SessionEntity): SessionDto {
        return SessionDto(
            sessionEntity.id,
            sessionEntity.subjectEntity.id,
            sessionEntity.duration,
            sessionEntity.dateEnd,
            sessionEntity.dateCreation,
            sessionEntity.dateUpdate,
            sessionEntity.isActive
        )
    }

    fun listToDto(listEntity: List<SessionEntity>): MutableList<SessionDto> {
        if (listEntity.isEmpty()) {
            throw SessionNotFoundException(0L)
        }
        val dtoList = mutableListOf<SessionDto>()

        listEntity.forEach {
            dtoList.add(toDto(it))
        }

        return dtoList
    }
}
