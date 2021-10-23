package com.ss.challenge.votesessionmanagerapi.repository.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserRepository : JpaRepository<UserEntity, Long> {
    fun findByCpf(cpf: String): Optional<UserEntity>
}
