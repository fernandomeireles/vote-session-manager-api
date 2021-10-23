package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectEndpoint
import com.ss.challenge.votesessionmanagerapi.service.session.exception.SessionNotClosedException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class SessionEndpointIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var sessionEndpoint: SessionEndpoint

    @Autowired
    lateinit var subjectEndpoint: SubjectEndpoint

    @Test
    fun `Should run a correct life cycle of a new session`() {

        val subject = subjectEndpoint.create("Teste")
        val newSession = subject.idSubject?.let { sessionEndpoint.create(it, 1L) }

        Assertions.assertNotNull(newSession)

        val findSession = newSession?.idSession?.let { sessionEndpoint.getSession(it) }

        Assertions.assertNotNull(findSession)

        if (findSession != null) {
            Assertions.assertEquals(newSession.idSession, findSession.idSession)
            Assertions.assertEquals(newSession.dateEnd, findSession.dateEnd)
            Assertions.assertEquals(newSession.duration, findSession.duration)
            Assertions.assertEquals(newSession.dateCreation, findSession.dateCreation)
            Assertions.assertEquals(newSession.dateUpdate, findSession.dateUpdate)
            Assertions.assertEquals(newSession.subjectId, findSession.subjectId)
        }

        assertThrows<SessionNotClosedException> {
            if (newSession != null) {
                newSession.idSession?.let { sessionEndpoint.putCloseSession(it) }
            }
        }

        val newSession2 = subject.idSubject?.let { sessionEndpoint.create(it, 1L) }
        Assertions.assertNotNull(newSession2)

        val findAllSession = sessionEndpoint.getAllSessions()

        Assertions.assertNotNull(findAllSession)

        if (findAllSession != null) {
            Assertions.assertFalse(findAllSession.isEmpty())
            Assertions.assertEquals(2, findAllSession.size)
        }
    }
}
