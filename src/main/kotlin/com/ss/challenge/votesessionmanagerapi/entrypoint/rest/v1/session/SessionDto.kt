package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.session

import java.time.LocalDateTime

data class SessionDto(
    var idSession: Long?,
    var subjectId: Long?,
    var duration: Long,
    var dateEnd: LocalDateTime,
    var dateCreation: LocalDateTime,
    var dateUpdate: LocalDateTime,
    var isActive: Boolean
)
