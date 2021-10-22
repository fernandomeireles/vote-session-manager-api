package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user

import com.ss.challenge.votesessionmanagerapi.utils.IntegrationBaseTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserEndpointIntegrationTest : IntegrationBaseTest() {

    @Autowired
    lateinit var userEndpoint: UserEndpoint

    @Test
    fun `Should run a correct life cycle of a new user`() {

        val newUser = userEndpoint.create()

        Assertions.assertNotNull(newUser)

        val findUser = newUser.idUser?.let { userEndpoint.getUser(it) }

        val inactiveUser = findUser?.idUser?.let { userEndpoint.putInactiveUser(it) }
        Assertions.assertNotNull(inactiveUser)

        if (inactiveUser != null) {
            Assertions.assertFalse(inactiveUser.isActive)
        }
    }

    @Test
    fun `Should corrected create and list many users`() {
        val newUser = userEndpoint.create()
        val newUser2 = userEndpoint.create()

        Assertions.assertNotNull(newUser)
        Assertions.assertNotNull(newUser2)
        Assertions.assertNotEquals(newUser.idUser, newUser2.idUser)

        val listUser = userEndpoint.getAllUser()
        Assertions.assertNotNull(listUser)
        if (listUser != null) {
            Assertions.assertFalse(listUser.isEmpty())
        }
    }
}
