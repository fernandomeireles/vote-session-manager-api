package com.ss.challenge.votesessionmanagerapi.service.subject

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto

interface ISubjectService {

    fun create(subject: String): SubjectDto

    fun findSubjectActive(): List<SubjectDto>?

    fun find(idSubject: Long): SubjectDto

    fun inactiveSubject(idSubject: Long): SubjectDto?
}
