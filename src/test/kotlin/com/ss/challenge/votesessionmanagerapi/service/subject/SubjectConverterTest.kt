package com.ss.challenge.votesessionmanagerapi.service.subject

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.service.subject.converter.SubjectConverter
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class SubjectConverterTest {

    @InjectMockKs
    lateinit var subjectConverter: SubjectConverter

    @Test
    fun `Should convert single entity to Dto`() {

        val subjectDto = subjectConverter.toDto(SUBJECT_ENTITY)
        Assertions.assertNotNull(subjectDto)
        Assertions.assertEquals(SUBJECT_ENTITY.id, subjectDto.idSubject)
        Assertions.assertEquals(SUBJECT_ENTITY.subject, subjectDto.subject)
        Assertions.assertEquals(SUBJECT_ENTITY.dateCreation, subjectDto.dateCreated)
        Assertions.assertEquals(SUBJECT_ENTITY.dateUpdate, subjectDto.dateUpdate)
        Assertions.assertEquals(SUBJECT_ENTITY.isActive, subjectDto.isActive)
    }

    @Test
    fun `Should convert single dto to entity`() {

        val subjectEntity = subjectConverter.toEntity(SUBJECT_DTO)
        Assertions.assertNotNull(subjectEntity)
        Assertions.assertEquals(SUBJECT_DTO.idSubject, subjectEntity.id)
        Assertions.assertEquals(SUBJECT_DTO.subject, subjectEntity.subject)
        Assertions.assertEquals(SUBJECT_DTO.dateCreated, subjectEntity.dateCreation)
        Assertions.assertEquals(SUBJECT_DTO.dateUpdate, subjectEntity.dateUpdate)
        Assertions.assertEquals(SUBJECT_DTO.isActive, subjectEntity.isActive)
    }

    @Test
    fun `Should convert list entity to list Dto`() {

        val subjectDtoList =
            subjectConverter.listToDto(arrayListOf(SUBJECT_ENTITY, SUBJECT_ENTITY_2))

        Assertions.assertFalse(subjectDtoList.isEmpty())
        Assertions.assertEquals(SUBJECT_ENTITY.id, subjectDtoList[0].idSubject)
        Assertions.assertEquals(SUBJECT_ENTITY.subject, subjectDtoList[0].subject)
        Assertions.assertEquals(SUBJECT_ENTITY.dateCreation, subjectDtoList[0].dateCreated)
        Assertions.assertEquals(SUBJECT_ENTITY.dateUpdate, subjectDtoList[0].dateUpdate)
        Assertions.assertEquals(SUBJECT_ENTITY.isActive, subjectDtoList[0].isActive)
        Assertions.assertEquals(SUBJECT_ENTITY_2.id, subjectDtoList[1].idSubject)
        Assertions.assertEquals(SUBJECT_ENTITY_2.subject, subjectDtoList[1].subject)
        Assertions.assertEquals(SUBJECT_ENTITY_2.dateCreation, subjectDtoList[1].dateCreated)
        Assertions.assertEquals(SUBJECT_ENTITY_2.dateUpdate, subjectDtoList[1].dateUpdate)
        Assertions.assertEquals(SUBJECT_ENTITY_2.isActive, subjectDtoList[1].isActive)
    }

    @Test
    fun `Should convert throw exception when entity is empty`() {

        assertThrows<SubjectNotFoundException> {
            subjectConverter.listToDto(arrayListOf())
        }
    }

    companion object {
        val SUBJECT_ENTITY = SubjectEntity(
            0L,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )

        val SUBJECT_DTO = SubjectDto(
            0L,
            "Test",
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        )

        val SUBJECT_ENTITY_2 = SubjectEntity(
            2L,
            "Test2",
            LocalDateTime.now(),
            LocalDateTime.now(),
            false
        )
    }
}
