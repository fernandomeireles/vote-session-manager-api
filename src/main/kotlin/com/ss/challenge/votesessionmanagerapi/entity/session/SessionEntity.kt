package com.ss.challenge.votesessionmanagerapi.entity.session

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Entity(name = "SESSION")
data class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SESSION", unique = true)
    var id: Long?,

    @ManyToOne
    @JoinColumn(name = "ID_SUBJECT")
    var subjectEntity: SubjectEntity,

    @Column(name = "NUM_DURATION")
    var duration: Long,

    @Column(name = "DAT_END_SESSION")
    var dateEnd: LocalDateTime,

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
