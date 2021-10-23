package com.ss.challenge.votesessionmanagerapi.service.session

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.service.session.converter.SessionConverter
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.subject.converter.SubjectConverter
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class SessionConverterTest {
    @InjectMockKs
    lateinit var sessionConverter: SessionConverter

    @MockK
    lateinit var subjectConverter: SubjectConverter

    @Test
    fun `Should convert single entity to Dto`() {

        val sessionDto = sessionConverter.toDto(SESSION_ENTITY)
        Assertions.assertNotNull(sessionDto)
        Assertions.assertEquals(SESSION_ENTITY.id, sessionDto.idSession)
        Assertions.assertEquals(SESSION_ENTITY.subjectEntity.id, sessionDto.subjectId)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionDto.dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionDto.dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionDto.dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionDto.dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateEnd, sessionDto.dateEnd)
        Assertions.assertEquals(SESSION_ENTITY.isActive, sessionDto.isActive)
    }

    @Test
    fun `Should convert list entity to list Dto`() {

        val sessionDtoList =
            sessionConverter.listToDto(arrayListOf(SESSION_ENTITY, SESSION_ENTITY_2))

        Assertions.assertFalse(sessionDtoList.isEmpty())

        Assertions.assertEquals(SESSION_ENTITY.id, sessionDtoList[0].idSession)
        Assertions.assertEquals(SESSION_ENTITY.subjectEntity.id, sessionDtoList[0].subjectId)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionDtoList[0].dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionDtoList[0].dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionDtoList[0].dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionDtoList[0].dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateEnd, sessionDtoList[0].dateEnd)
        Assertions.assertEquals(SESSION_ENTITY.isActive, sessionDtoList[0].isActive)

        Assertions.assertEquals(SESSION_ENTITY_2.id, sessionDtoList[1].idSession)
        Assertions.assertEquals(SESSION_ENTITY_2.subjectEntity.id, sessionDtoList[1].subjectId)
        Assertions.assertEquals(SESSION_ENTITY_2.dateUpdate, sessionDtoList[1].dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY_2.dateUpdate, sessionDtoList[1].dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY_2.dateCreation, sessionDtoList[1].dateCreation)
        Assertions.assertEquals(SESSION_ENTITY_2.dateCreation, sessionDtoList[1].dateCreation)
        Assertions.assertEquals(SESSION_ENTITY_2.dateEnd, sessionDtoList[1].dateEnd)
        Assertions.assertEquals(SESSION_ENTITY_2.isActive, sessionDtoList[1].isActive)
    }

    @Test
    fun `Should convert throw exception when entity is empty`() {

        assertThrows<SessionNotFoundException> {
            sessionConverter.listToDto(arrayListOf())
        }
    }

    @Test
    fun `Should convert single Dto to Entity`() {
        val sessionDto = sessionConverter.toDto(SESSION_ENTITY)

        every {
            subjectConverter.toEntity(
                any()
            )
        } answers {
            SUBJECT_ENTITY
        }

        val sessionEntity = sessionConverter.toEntity(sessionDto, SUBJECT_DTO)

        Assertions.assertNotNull(sessionEntity)
        Assertions.assertEquals(SESSION_ENTITY.id, sessionEntity.id)
        Assertions.assertEquals(SESSION_ENTITY.subjectEntity.id, sessionEntity.subjectEntity.id)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionEntity.dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateUpdate, sessionEntity.dateUpdate)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionEntity.dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateCreation, sessionEntity.dateCreation)
        Assertions.assertEquals(SESSION_ENTITY.dateEnd, sessionEntity.dateEnd)
        Assertions.assertEquals(SESSION_ENTITY.isActive, sessionEntity.isActive)
    }

    companion object {
        val SESSION_ENTITY = SessionEntity(
            0L,
            SubjectEntity(
                0L,
                "Test",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            ),
            10L,
            LocalDateTime.now().plusMinutes(10L),
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )

        val SESSION_ENTITY_2 = SessionEntity(
            1L,
            SubjectEntity(
                1L,
                "Test2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            ),
            11L,
            LocalDateTime.now().plusMinutes(11L),
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        )

        val SUBJECT_DTO = SubjectDto(
            0L,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )

        val SUBJECT_ENTITY = SubjectEntity(
            0L,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )
    }
}
