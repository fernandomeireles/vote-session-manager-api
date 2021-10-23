package com.ss.challenge.votesessionmanagerapi.service.vote

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto
import com.ss.challenge.votesessionmanagerapi.service.session.ISessionService
import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.service.user.IUserService
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteInvalidException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class VoteServiceIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var userService: IUserService

    @Autowired
    lateinit var subjectService: ISubjectService

    @Autowired
    lateinit var sessionService: ISessionService

    @Autowired
    lateinit var voteService: VoteService

    private lateinit var user: UserDto
    private lateinit var session: SessionDto
    private lateinit var subject: SubjectDto

    @BeforeEach
    fun init() {
        user = userService.create()
        subject = subjectService.create("Test")
        session = subject.idSubject?.let { sessionService.create(it, 1L) }!!
    }

    @Test
    fun `Should throw exception when vote created in a closed session`() {

        val sessionNew = session.idSession?.let {
            sessionService.inactiveSession(
                it,
                LocalDateTime.now().plusMinutes(10L)
            )
        }

        assertThrows<VoteInvalidException> {
            sessionNew?.idSession?.let {
                user.idUser?.let { it1 ->
                    voteService.create(
                        it,
                        it1, true
                    )
                }
            }
        }
    }

    @Test
    fun `Should throw exception when vote created by inactive user`() {

        val userNew = user.idUser?.let { userService.inactiveUser(it) }

        assertThrows<VoteInvalidException> {
            session.idSession?.let {
                userNew?.idUser?.let { it1 ->
                    voteService.create(
                        it,
                        it1, false
                    )
                }
            }
        }
    }

    @Test
    fun `Should throw exception when user try duplicated vote`() {

        session.idSession?.let {
            user.idUser?.let { it1 ->
                voteService.create(
                    it,
                    it1, true
                )
            }
        }

        assertThrows<VoteInvalidException> {
            session.idSession?.let {
                user.idUser?.let { it1 ->
                    voteService.create(
                        it,
                        it1, true
                    )
                }
            }
        }
    }

    @Test
    fun `Should return correct votes by session`() {

        val user2 = userService.create()

        val vote1 = session.idSession?.let {
            user.idUser?.let { it1 ->
                voteService.create(
                    it,
                    it1, true
                )
            }
        }

        Assertions.assertNotNull(vote1)
        val vote2 = session.idSession?.let {
            user2.idUser?.let { it1 ->
                voteService.create(
                    it,
                    it1, true
                )
            }
        }

        Assertions.assertNotNull(vote2)

        val voteBySession = session.idSession?.let { voteService.findVoteBySessionEntity(it) }

        Assertions.assertNotNull(voteBySession)

        if (voteBySession != null) {
            Assertions.assertFalse(voteBySession.isEmpty())
            Assertions.assertEquals(2, voteBySession.size)
        }
    }
}
