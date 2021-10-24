package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserEndpoint
import com.ss.challenge.votesessionmanagerapi.service.vote.exception.VoteCapacityExceededException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.awaitility.Awaitility
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.LocalDateTime

class VoteEndpointIntegrationTest : IntegrationBaseTest() {
    @Autowired
    lateinit var sessionEndpoint: SessionEndpoint

    @Autowired
    lateinit var subjectEndpoint: SubjectEndpoint

    @Autowired
    lateinit var userEndpoint: UserEndpoint

    @Autowired
    lateinit var voteEndpoint: VoteEndpoint

    @Test
    fun `Should run a correct life cycle of a new vote session`() {
        val subject = subjectEndpoint.create("Test")
        Assertions.assertNotNull(subject)

        val session = subject.idSubject?.let { sessionEndpoint.create(it, 3L) }

        val user = userEndpoint.create()

        val newVote = session?.idSession?.let {
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
    fun `Should run a correct life cycle of a new vote session for 180 votes por minute`() {
        val subject = subjectEndpoint.create("Test")
        Assertions.assertNotNull(subject)

        val session = subject.idSubject?.let { sessionEndpoint.create(it, 3L) }

        for (i in 1..180) {
            userEndpoint.create()
        }

        val sessionId = session?.idSession ?: 0L

        val allUsers = userEndpoint.getAllUser()
        Assertions.assertNotNull(allUsers)
        if (allUsers != null) {
            Assertions.assertFalse(allUsers.isEmpty())

            allUsers.forEach {
                it.idUser?.let { it1 -> voteEndpoint.create(sessionId, it1, true) }
            }
        }
    }

    @Test
    fun `Should throw exception when vote session received 185 votes por minute`() {
        val subject = subjectEndpoint.create("Test")
        Assertions.assertNotNull(subject)

        val session = subject.idSubject?.let { sessionEndpoint.create(it, 3L) }

        for (i in 1..185) {
            userEndpoint.create()
        }

        val sessionId = session?.idSession ?: 0L

        val allUsers = userEndpoint.getAllUser()
        Assertions.assertNotNull(allUsers)
        if (allUsers != null) {
            Assertions.assertFalse(allUsers.isEmpty())

            assertThrows<VoteCapacityExceededException> {
                allUsers.forEach {
                    it.idUser?.let { it1 -> voteEndpoint.create(sessionId, it1, true) }
                }
            }
        }
    }

    @Test
    fun `Should not throw exception when vote session received 185 votes`() {
        val subject = subjectEndpoint.create("Test")
        Assertions.assertNotNull(subject)

        val session = subject.idSubject?.let { sessionEndpoint.create(it, 3L) }

        for (i in 1..185) {
            userEndpoint.create()
        }

        val sessionId = session?.idSession ?: 0L

        val allUsers = userEndpoint.getAllUser()
        Assertions.assertNotNull(allUsers)
        if (allUsers != null) {
            Assertions.assertFalse(allUsers.isEmpty())

            allUsers.forEach {

                if (it.idUser == 179L) {
                    Awaitility.setDefaultTimeout(Duration.ofSeconds(63))
                    val future = LocalDateTime.now().plusSeconds(61)
                    Awaitility.await().until { LocalDateTime.now().isAfter(future) }
                }

                it.idUser?.let { it1 -> voteEndpoint.create(sessionId, it1, true) }
            }
        }
    }
}
