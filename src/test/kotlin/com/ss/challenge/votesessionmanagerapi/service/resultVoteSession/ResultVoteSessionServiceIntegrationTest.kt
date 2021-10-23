package com.ss.challenge.votesessionmanagerapi.service.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote.VoteDto
import com.ss.challenge.votesessionmanagerapi.service.session.ISessionService
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotClosedException
import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.service.user.IUserService
import com.ss.challenge.votesessionmanagerapi.service.vote.IVoteService
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteNotFoundException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.awaitility.Awaitility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.LocalDateTime

class ResultVoteSessionServiceIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var userService: IUserService

    @Autowired
    lateinit var subjectService: ISubjectService

    @Autowired
    lateinit var sessionService: ISessionService

    @Autowired
    lateinit var voteService: IVoteService

    @Autowired
    lateinit var resultVoteSessionService: ResultVoteSessionService

    private lateinit var user: UserDto
    private lateinit var session: SessionDto
    private lateinit var subject: SubjectDto
    private lateinit var vote: VoteDto

    @BeforeEach
    fun init() {
        user = userService.create()
        subject = subjectService.create("Test")
        session = subject.idSubject?.let { sessionService.create(it, 1L) }!!
        vote = session.idSession?.let {
            user.idUser?.let { it1 ->
                voteService.create(
                    it,
                    it1, true
                )
            }
        }!!
    }

    @Test
    fun `Should throw exception when vote session is open`() {
        assertThrows<SessionNotClosedException> {
            session.idSession?.let { resultVoteSessionService.getResultVoteSession(it) }
        }
    }

    @Test
    fun `Should throw exception when vote session is empty`() {
        val newSession = subject.idSubject?.let { sessionService.create(it, 1L) }!!
        Assertions.assertNotNull(newSession)
        Assertions.assertNotEquals(newSession.idSession, session.idSession)

        Awaitility.setDefaultTimeout(Duration.ofSeconds(63))
        val future = LocalDateTime.now().plusSeconds(61)
        Awaitility.await().until { LocalDateTime.now().isAfter(future) }

        assertThrows<VoteNotFoundException> {
            newSession.idSession?.let { resultVoteSessionService.getResultVoteSession(it) }
        }
    }
}
