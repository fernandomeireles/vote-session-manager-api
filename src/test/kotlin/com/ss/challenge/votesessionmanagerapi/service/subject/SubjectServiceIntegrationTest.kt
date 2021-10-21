package com.ss.challenge.votesessionmanagerapi.service.subject

import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class SubjectServiceIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var service: SubjectService

    @Test
    @Order(1)
    fun `Should throw error when not found subject id`() {

        assertThrows<SubjectNotFoundException> { val subject = service.find(999L) }
    }

    @Test
    @Order(2)
    fun `Should throw error when not found active subjects`() {

        assertThrows<SubjectNotFoundException> { val subject = service.findSubjectActive() }
    }

    @Test
    @Order(3)
    fun `Should not persist duplicated id for subject`() {

        val subject1 = service.create("Teste")
        val subject2 = service.create("Teste2")

        Assertions.assertNotEquals(subject1.idSubject, subject2.idSubject)
    }
}
