package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto
import com.ss.challenge.votesessionmanagerapi.repository.user.UserRepository
import com.ss.challenge.votesessionmanagerapi.service.user.converter.UserConverter
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userConverter: UserConverter,
    private val userRepository: UserRepository
) : IUserService {
    override fun create(): UserDto {
        return userConverter.toDto(userRepository.create())
    }

    override fun findAll(): List<UserDto> {
        return userConverter.listToDto(userRepository.findAll())
    }

    override fun findById(userId: Long): UserDto {
        return userConverter.toDto(userRepository.findById(userId))
    }

    override fun inactiveUser(userId: Long): UserDto {
        val user = userRepository.findById(userId)
        user.isActive = false
        return userConverter.toDto(userRepository.update(user))
    }
}
