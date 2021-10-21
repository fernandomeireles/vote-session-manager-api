package com.ss.challenge.votesessionmanagerapi.core.usercase.subject

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Entity(name = "SUBJECT")
data class SubjectEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_SUBJECT", unique = true)
    var id: Long?,

    @Column(name = "DES_SUBJECT")
    var subject: String,

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
