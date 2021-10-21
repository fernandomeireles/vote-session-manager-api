package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject

import java.time.LocalDateTime

data class SubjectDto(
    val idSubject: Long?,
    val subject: String,
    val dateCreated: LocalDateTime,
    val dateUpdate: LocalDateTime,
    val isActive: Boolean
)
