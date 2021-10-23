package com.ss.challenge.votesessionmanagerapi.repository.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserCpfNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserNotFoundException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserRepository(private val repository: IUserRepository) {

    fun create(): UserEntity {
        return repository.save(
            UserEntity(
                null,
                "",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            )
        )
    }

    fun create(cpf: String): UserEntity {
        return repository.save(
            UserEntity(
                null,
                cpf,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            )
        )
    }

    fun update(userEntity: UserEntity): UserEntity {
        return repository.save(userEntity)
    }

    fun findAll(): List<UserEntity> {
        return repository.findAll()
    }

    fun findById(userId: Long): UserEntity {
        return repository.findById(userId).orElseThrow {
            throw UserNotFoundException(userId)
        }
    }

    fun findByCpf(cpf: String): UserEntity {
        return repository.findByCpf(cpf).orElseThrow {
            throw UserCpfNotFoundException(cpf)
        }
    }
}
