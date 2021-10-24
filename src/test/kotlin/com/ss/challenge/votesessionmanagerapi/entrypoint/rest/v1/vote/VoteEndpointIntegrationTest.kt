package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserEndpoint
import com.ss.challenge.votesessionmanagerapi.service.vote.IVoteService
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class VoteEndpointIntegrationTest : IntegrationBaseTest() {
    @Autowired
    lateinit var sessionEndpoint: SessionEndpoint

    @Autowired
    lateinit var subjectEndpoint: SubjectEndpoint

    @Autowired
    lateinit var userEndpoint: UserEndpoint

    @Autowired
    lateinit var voteEndpoint: VoteEndpoint

    @Autowired
    lateinit var voteService: IVoteService

    private lateinit var subject: SubjectDto
    private lateinit var session: SessionDto

    @BeforeEach
    fun init() {
        subject = subjectEndpoint.create("Test")
        session = subject.idSubject?.let { sessionEndpoint.create(it, 3L) }!!
    }

    @Test
    @Order(1)
    fun `Should run a correct life cycle of a new vote session`() {

        val user = userEndpoint.create()

        val newVote = session.idSession?.let {
            user.idUser?.let { it1 ->
                voteEndpoint.create(
                    it,
                    it1, true
                )
            }
        }

        Assertions.assertNotNull(newVote)
        if (newVote != null) {
            Assertions.assertEquals(true, newVote.isApprovedSession)
        }
    }

    @Test
    @Order(2)
    fun `Should run a correct life cycle of a new vote session for 500 votes por minute`() {

        for (i in 1..500) {
            userEndpoint.create()
        }

        val sessionId = session.idSession ?: 0L

        val allUsers = userEndpoint.getAllUser()
        Assertions.assertNotNull(allUsers)
        if (allUsers != null) {
            Assertions.assertFalse(allUsers.isEmpty())

            allUsers.parallelStream().forEach() {
                it.idUser?.let { it1 -> voteEndpoint.create(sessionId, it1, true) }
            }
        }

        val voteBySession = voteService.findVoteBySessionEntity(sessionId)

        Assertions.assertEquals(500, voteBySession.size)
    }
}
