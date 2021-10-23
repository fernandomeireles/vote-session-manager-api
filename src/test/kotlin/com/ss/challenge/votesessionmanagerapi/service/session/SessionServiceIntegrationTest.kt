package com.ss.challenge.votesessionmanagerapi.service.session

import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class SessionServiceIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var sessionService: SessionService

    @Autowired
    lateinit var subjectService: ISubjectService

    @Test
    fun `Should correct valid session`() {
        val subject = subjectService.create("Test")
        val newSession = subject.idSubject?.let { sessionService.create(it, 10L) }

        Assertions.assertNotNull(newSession)
        val idNewSession = newSession?.idSession ?: 0L
        val endDate = newSession?.dateEnd ?: LocalDateTime.now()

        Assertions.assertFalse(
            sessionService.validActiveSession(
                idNewSession,
                LocalDateTime.now().plusMinutes(11)
            )
        )

        Assertions.assertTrue(
            sessionService.validActiveSession(
                idNewSession,
                LocalDateTime.now().plusMinutes(9)
            )
        )

        Assertions.assertTrue(
            sessionService.validActiveSession(
                idNewSession,
                endDate
            )
        )
    }
}
