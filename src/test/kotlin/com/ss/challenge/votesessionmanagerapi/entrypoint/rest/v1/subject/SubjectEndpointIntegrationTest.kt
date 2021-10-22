package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject

import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class SubjectEndpointIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var subjectEndpoint: SubjectEndpoint

    @Test
    fun `Should run a correct life cycle of a new subject`() {

        val newSubject = subjectEndpoint.create("Test")
        Assertions.assertEquals("Test", newSubject.subject)
        Assertions.assertTrue(newSubject.isActive)

        val subjectFind = newSubject.idSubject?.let { subjectEndpoint.getSubject(it) }

        Assertions.assertNotNull(subjectFind)

        if (subjectFind != null) {
            Assertions.assertEquals("Test", subjectFind.subject)
            Assertions.assertTrue(subjectFind.isActive)
        }

        val activedSubjects = subjectEndpoint.getActiveSubjects()

        if (activedSubjects != null) {
            Assertions.assertFalse(activedSubjects.isEmpty())
            activedSubjects.forEach {
                it.idSubject?.let { it1 -> subjectEndpoint.putInactiveSubject(it1) }
            }
        }

        assertThrows<SubjectNotFoundException> {
            subjectEndpoint.getActiveSubjects()
        }
    }
}
