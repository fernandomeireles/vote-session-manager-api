package com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession

import com.ss.challenge.votesessionmanagerapi.entity.session.SessionEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity(name = "RESULT_VOTE_SESSION")
data class ResultVoteSession(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_RESULT_SESSION", unique = true)
    var id: Long?,

    @OneToOne
    @JoinColumn(name = "ID_SESSION")
    var sessionEntity: SessionEntity,

    @Column(name = "NUM_POSITIVE_VOTE")
    var positiveVote: Long,

    @Column(name = "NUM_NEGATIVE_VOTE")
    var negativeVote: Long,

    @Column(name = "RESULT_SESSION")
    var status: String,

    @Column(name = "DAT_CREATION")
    var dateCreation: LocalDateTime,

    @Column(name = "DAT_UPDATE")
    var dateUpdate: LocalDateTime,

    @Column(name = "FLG_ACTIVE")
    var isActive: Boolean

)
