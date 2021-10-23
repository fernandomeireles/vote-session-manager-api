package com.ss.challenge.votesessionmanagerapi.service.session.converter

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.subject.converter.SubjectConverter
import org.springframework.stereotype.Component

@Component
class SessionConverter(private val subjectConverter: SubjectConverter) {

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

    fun toEntity(sessionDto: SessionDto, subjectDto: SubjectDto): SessionEntity {
        return SessionEntity(
            sessionDto.idSession,
            subjectConverter.toEntity(
                subjectDto
            ),
            sessionDto.duration,
            sessionDto.dateEnd,
            sessionDto.dateCreation,
            sessionDto.dateUpdate,
            sessionDto.isActive
        )
    }
}
