package com.ss.challenge.votesessionmanagerapi.service.user

import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserCpfNotFoundException
import com.ss.challenge.votesessionmanagerapi.service.user.exception.UserInvalidCpfException
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
        assertThrows<UserNotFoundException> { service.findById(999L) }
    }

    @Test
    @Order(2)
    fun `Should not persisted duplicated id user`() {
        val user1 = service.create()
        val user2 = service.create()
        val user3 = service.createWithCpf()
        val user4 = service.createWithCpf()

        Assertions.assertNotEquals(user1.idUser, user2.idUser)
        Assertions.assertNotEquals(user3.idUser, user4.idUser)
    }

    @Test
    @Order(3)
    fun `Should throw error when not found user cpf`() {
        assertThrows<UserCpfNotFoundException> { service.findByCpf("123435") }

        assertThrows<UserInvalidCpfException> { service.findByCpf("") }
    }
}
