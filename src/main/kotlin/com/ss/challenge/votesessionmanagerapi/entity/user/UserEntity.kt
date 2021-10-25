package com.ss.challenge.votesessionmanagerapi.entity.user

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Entity(name = "USER")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER", unique = true)
    var id: Long?,

    @Column(name = "DES_CPF")
    var cpf: String,

    @Column(name = "DAT_CREATION")
    var dateCreation: LocalDateTime,

    @Column(name = "DAT_UPDATE")
    var dateUpdate: LocalDateTime,

    @Column(name = "FLG_ACTIVE")
    var isActive: Boolean
) : Serializable {
    @PrePersist
    fun persist() {
        dateCreation = LocalDateTime.now()
        dateUpdate = LocalDateTime.now()
    }

    @PreUpdate
    fun update() {
        dateUpdate = LocalDateTime.now()
    }
}
