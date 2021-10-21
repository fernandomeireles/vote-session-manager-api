package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject

import java.time.LocalDateTime

data class SubjectDto(
    val subject: String,
    val dateCreated: LocalDateTime,
    val isActive: Boolean,
    val idSubject: Long
)
