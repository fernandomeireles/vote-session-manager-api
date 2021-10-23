package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultStatusEnum
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote.VoteEndpoint
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.awaitility.Awaitility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.LocalDateTime

class ResultVoteSessionIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var sessionEndpoint: SessionEndpoint

    @Autowired
    lateinit var subjectEndpoint: SubjectEndpoint

    @Autowired
    lateinit var userEndpoint: UserEndpoint

    @Autowired
    lateinit var voteEndpoint: VoteEndpoint

    @Autowired
    lateinit var resultVoteSessionEndpoint: ResultVoteSessionEndpoint

    @Test
    fun `Should run a correct result vote session`() {
        val subject = subjectEndpoint.create("Test")
        Assertions.assertNotNull(subject)

        val session = subject.idSubject?.let { sessionEndpoint.create(it, 1L) }
        Assertions.assertNotNull(session)

        val user = userEndpoint.create()
        Assertions.assertNotNull(user)

        val user2 = userEndpoint.create()
        Assertions.assertNotNull(user2)

        val user3 = userEndpoint.create()
        Assertions.assertNotNull(user3)

        val newVote = session?.idSession?.let {
            user.idUser?.let { it1 ->
                voteEndpoint.create(
                    it,
                    it1, true
                )
            }
        }
        Assertions.assertNotNull(newVote)

        val newVote2 = session?.idSession?.let {
            user2.idUser?.let { it1 ->
                voteEndpoint.create(
                    it,
                    it1, false
                )
            }
        }
        Assertions.assertNotNull(newVote2)

        val newVote3 = session?.idSession?.let {
            user3.idUser?.let { it1 ->
                voteEndpoint.create(
                    it,
                    it1, true
                )
            }
        }
        Assertions.assertNotNull(newVote3)

        Awaitility.setDefaultTimeout(Duration.ofSeconds(63))
        val future = LocalDateTime.now().plusSeconds(61)
        Awaitility.await().until { LocalDateTime.now().isAfter(future) }

        val resultVoteSession = session?.idSession?.let {
            resultVoteSessionEndpoint.getResultVoteSession(
                it
            )
        }

        Assertions.assertNotNull(resultVoteSession)

        if (resultVoteSession != null) {
            Assertions.assertEquals(
                ResultStatusEnum.APPROVED_SESSION,
                ResultStatusEnum.valueOf(resultVoteSession.status)
            )
            Assertions.assertEquals(2L, resultVoteSession.positiveVote)
            Assertions.assertEquals(1L, resultVoteSession.negativeVote)
        }
    }
}
