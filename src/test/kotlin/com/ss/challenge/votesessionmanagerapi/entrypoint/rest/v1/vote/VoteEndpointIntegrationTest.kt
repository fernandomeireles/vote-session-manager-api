package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.vote

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session.SessionEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectEndpoint
import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user.UserEndpoint
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
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
}
