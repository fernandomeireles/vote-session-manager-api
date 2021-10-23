package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.service.user.converter.UserConverter
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserNotFoundException
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class UserConverterTest {
    @InjectMockKs
    lateinit var userConverter: UserConverter

    @Test
    fun `Should convert single entity to Dto`() {
        val userDto = userConverter.toDto(USER_ENTITY)

        Assertions.assertNotNull(userDto)
        Assertions.assertEquals(USER_ENTITY.id, userDto.idUser)
        Assertions.assertEquals(USER_ENTITY.cpf, userDto.cpf)
        Assertions.assertEquals(USER_ENTITY.dateCreation, userDto.dateCreation)
        Assertions.assertEquals(USER_ENTITY.dateUpdate, userDto.dateUpdate)
        Assertions.assertEquals(USER_ENTITY.isActive, userDto.isActive)
    }

    @Test
    fun `Should convert list entity to list Dto`() {
        val userDtoList = userConverter.listToDto(arrayListOf(USER_ENTITY, USER_ENTITY_2))

        Assertions.assertFalse(userDtoList.isEmpty())
        Assertions.assertEquals(USER_ENTITY.id, userDtoList[0].idUser)
        Assertions.assertEquals(USER_ENTITY.cpf, userDtoList[0].cpf)
        Assertions.assertEquals(USER_ENTITY.dateCreation, userDtoList[0].dateCreation)
        Assertions.assertEquals(USER_ENTITY.dateUpdate, userDtoList[0].dateUpdate)
        Assertions.assertEquals(USER_ENTITY.isActive, userDtoList[0].isActive)

        Assertions.assertEquals(USER_ENTITY_2.id, userDtoList[1].idUser)
        Assertions.assertEquals(USER_ENTITY_2.cpf, userDtoList[1].cpf)
        Assertions.assertEquals(USER_ENTITY_2.dateCreation, userDtoList[1].dateCreation)
        Assertions.assertEquals(USER_ENTITY_2.dateUpdate, userDtoList[1].dateUpdate)
        Assertions.assertEquals(USER_ENTITY_2.isActive, userDtoList[1].isActive)
    }

    @Test
    fun `Should convert throw exception when entity is empty`() {

        assertThrows<UserNotFoundException> {
            userConverter.listToDto(arrayListOf())
        }
    }

    companion object {
        val USER_ENTITY = UserEntity(
            0L,
            "123",
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )

        val USER_ENTITY_2 = UserEntity(
            1L,
            "1234",
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        )
    }
}
