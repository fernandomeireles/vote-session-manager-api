package com.ss.challenge.votesessionmanagerapi.repository.user

import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IUserRepository : JpaRepository<UserEntity, Long>
