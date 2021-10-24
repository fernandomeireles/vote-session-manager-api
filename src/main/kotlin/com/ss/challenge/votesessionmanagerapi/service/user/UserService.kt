package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserValidCpfEnum
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto
import com.ss.challenge.votesessionmanagerapi.repository.user.UserRepository
import com.ss.challenge.votesessionmanagerapi.service.cpfGenerator.CpfGeneratorService
import com.ss.challenge.votesessionmanagerapi.service.user.converter.UserConverter
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserInvalidCpfException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userConverter: UserConverter,
    private val userRepository: UserRepository,
    private val cpfGeneratorService: CpfGeneratorService
) : IUserService {
    override fun create(): UserDto {
        return userConverter.toDto(userRepository.create())
    }

    override fun createWithCpf(): UserDto {

        val cpf = cpfGeneratorService.getCpf()

        return userConverter.toDto(userRepository.create(cpf.number_formatted))
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

    override fun findByCpf(cpf: String): UserDto {
        if (cpf == "") {
            throw UserInvalidCpfException("")
        }

        return userConverter.toDto(userRepository.findByCpf(cpf))
    }

    override fun validateCpf(cpf: String): UserValidCpfEnum {

        return if (cpfGeneratorService.validateCpf(cpf).status) {
            UserValidCpfEnum.ABLE_TO_VOTE
        } else {
            UserValidCpfEnum.UNABLE_TO_VOTE
        }
    }
}
