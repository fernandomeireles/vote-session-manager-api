package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserNotFoundException
import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class UserServiceIntegrationTest : IntegrationBaseTest() {
    @Autowired
    lateinit var service: UserService

    @Test
    @Order(1)
    fun `Should throw error when not found user id`() {
        assertThrows<UserNotFoundException> { val user = service.findById(999L) }
    }

    @Test
    @Order(2)
    fun `Should not persisted duplicated id user`() {
        val user1 = service.create()
        val user2 = service.create()

        Assertions.assertNotEquals(user1.idUser, user2.idUser)
    }
}
