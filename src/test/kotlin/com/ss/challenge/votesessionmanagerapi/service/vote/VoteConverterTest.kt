package com.ss.challenge.votesessionmanagerapi.service.vote

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import com.ss.challenge.votesessionmanagerapi.entity.vote.VoteEntity
import com.ss.challenge.votesessionmanagerapi.service.vote.converter.VoteConverter
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteNotFoundException
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class VoteConverterTest {

    @InjectMockKs
    lateinit var voteConverter: VoteConverter

    @Test
    fun `Should convert single entity to Dto`() {
        val voteDto = voteConverter.toDto(VOTE_ENTITY)

        Assertions.assertNotNull(voteDto)
        Assertions.assertEquals(VOTE_ENTITY.id, voteDto.idVote)
        Assertions.assertEquals(VOTE_ENTITY.sessionEntity.id, voteDto.sessionId)
        Assertions.assertEquals(VOTE_ENTITY.userEntity.id, voteDto.userId)
        Assertions.assertEquals(VOTE_ENTITY.dateUpdate, voteDto.dateUpdate)
        Assertions.assertEquals(VOTE_ENTITY.dateCreation, voteDto.dateCreation)
        Assertions.assertEquals(VOTE_ENTITY.isValid, voteDto.isValid)
        Assertions.assertEquals(VOTE_ENTITY.isApproved, voteDto.isApproved)
    }

    @Test
    fun `Should convert list entity to list Dto`() {
        val voteListDto = voteConverter.listToDto(arrayListOf(VOTE_ENTITY, VOTE_ENTITY_2))
        Assertions.assertFalse(voteListDto.isEmpty())

        Assertions.assertEquals(VOTE_ENTITY.id, voteListDto[0].idVote)
        Assertions.assertEquals(VOTE_ENTITY.sessionEntity.id, voteListDto[0].sessionId)
        Assertions.assertEquals(VOTE_ENTITY.userEntity.id, voteListDto[0].userId)
        Assertions.assertEquals(VOTE_ENTITY.dateUpdate, voteListDto[0].dateUpdate)
        Assertions.assertEquals(VOTE_ENTITY.dateCreation, voteListDto[0].dateCreation)
        Assertions.assertEquals(VOTE_ENTITY.isValid, voteListDto[0].isValid)
        Assertions.assertEquals(VOTE_ENTITY.isApproved, voteListDto[0].isApproved)

        Assertions.assertEquals(VOTE_ENTITY_2.id, voteListDto[1].idVote)
        Assertions.assertEquals(VOTE_ENTITY_2.sessionEntity.id, voteListDto[1].sessionId)
        Assertions.assertEquals(VOTE_ENTITY_2.userEntity.id, voteListDto[1].userId)
        Assertions.assertEquals(VOTE_ENTITY_2.dateUpdate, voteListDto[1].dateUpdate)
        Assertions.assertEquals(VOTE_ENTITY_2.dateCreation, voteListDto[1].dateCreation)
        Assertions.assertEquals(VOTE_ENTITY_2.isValid, voteListDto[1].isValid)
        Assertions.assertEquals(VOTE_ENTITY_2.isApproved, voteListDto[1].isApproved)
    }

    @Test
    fun `Should convert throw exception when entity is empty`() {

        assertThrows<VoteNotFoundException> {
            val voteDtoList =
                voteConverter.listToDto(arrayListOf())
        }
    }

    companion object {
        val VOTE_ENTITY = VoteEntity(
            0L,
            UserEntity(
                0L,
                "",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            ),
            SessionEntity(
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
            ),
            true,
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val VOTE_ENTITY_2 = VoteEntity(
            1L,
            UserEntity(
                1L,
                "",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            ),
            SessionEntity(
                1L,
                SubjectEntity(
                    1L,
                    "Test1",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
                ),
                110L,
                LocalDateTime.now().plusMinutes(110L),
                LocalDateTime.now(),
                LocalDateTime.now(),
                false
            ),
            false,
            false,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    }
}
