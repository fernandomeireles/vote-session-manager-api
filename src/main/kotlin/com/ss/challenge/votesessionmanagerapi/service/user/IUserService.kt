package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserValidCpfEnum
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto

interface IUserService {

    fun create(): UserDto

    fun createWithCpf(): UserDto

    fun findAll(): List<UserDto>?

    fun findById(userId: Long): UserDto

    fun inactiveUser(userId: Long): UserDto?

    fun findByCpf(cpf: String): UserDto

    fun validateCpf(cpf: String): UserValidCpfEnum
}
