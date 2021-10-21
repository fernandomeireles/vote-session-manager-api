package com.ss.challenge.votesessionmanagerapi.entity.vote

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import com.ss.challenge.votesessionmanagerapi.entity.user.UserEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Entity(name = "VOTE")
data class VoteEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_VOTE", unique = true)
    var id: Long?,

    @OneToOne
    @JoinColumn(name = "ID_USER")
    var userEntity: UserEntity,

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    var sessionEntity: SessionEntity,

    @Column(name = "FLG_VALID")
    var isValid: Boolean,

    @Column(name = "FLG_APPROVED")
    var isApproved: Boolean,

    @Column(name = "DAT_CREATION")
    var dateCreation: LocalDateTime,

    @Column(name = "DAT_UPDATE")
    var dateUpdate: LocalDateTime

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
