package com.ss.challenge.votesessionmanagerapi.service.vote.converter

import com.ss.challenge.votesessionmanagerapi.entity.vote.VoteEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote.VoteDto
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteNotFoundException
import org.springframework.stereotype.Component

@Component
class VoteConverter {

    fun toDto(voteEntity: VoteEntity): VoteDto {
        return VoteDto(
            voteEntity.id,
            voteEntity.userEntity.id,
            voteEntity.sessionEntity.id,
            voteEntity.isValid,
            voteEntity.isApproved,
            voteEntity.dateCreation,
            voteEntity.dateUpdate
        )
    }

    fun listToDto(listEntity: List<VoteEntity>): MutableList<VoteDto> {
        if (listEntity.isEmpty()) {
            throw VoteNotFoundException(0L)
        }
        val dtoList = mutableListOf<VoteDto>()

        listEntity.forEach {
            dtoList.add(toDto(it))
        }

        return dtoList
    }
}
