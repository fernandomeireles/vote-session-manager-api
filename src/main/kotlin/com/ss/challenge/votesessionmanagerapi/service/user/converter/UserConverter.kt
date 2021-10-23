package com.ss.challenge.votesessionmanagerapi.service.user.converter

import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class UserConverter {

    fun toDto(userEntity: UserEntity): UserDto {
        return UserDto(
            userEntity.id,
            userEntity.cpf,
            userEntity.dateCreation,
            userEntity.dateUpdate,
            userEntity.isActive
        )
    }

    fun listToDto(listEntity: List<UserEntity>): MutableList<UserDto> {
        if (listEntity.isEmpty()) {
            throw UserNotFoundException(0L)
        }
        val dtoList = mutableListOf<UserDto>()

        listEntity.forEach {
            dtoList.add(toDto(it))
        }

        return dtoList
    }

    fun toEntity(dto: UserDto): UserEntity {
        return UserEntity(
            dto.idUser,
            dto.cpf ?: "",
            dto.dateCreation,
            dto.dateUpdate,
            dto.isActive
        )
    }
}
