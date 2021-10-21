package com.ss.challenge.votesessionmanagerapi.service.subject.converter

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import org.springframework.stereotype.Component

@Component
class SubjectConverter {

    fun toDto(entity: SubjectEntity): SubjectDto {
        return SubjectDto(
            entity.id,
            entity.subject,
            entity.dateCreation,
            entity.dateUpdate,
            entity.isActive
        )
    }

    fun listToDto(listEntity: List<SubjectEntity>): MutableList<SubjectDto> {
        if (listEntity.isEmpty()) {
            throw SubjectNotFoundException(0L)
        }
        val dtoList = mutableListOf<SubjectDto>()

        listEntity.forEach {
            dtoList.add(toDto(it))
        }

        return dtoList
    }
}
