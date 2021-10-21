package com.ss.challenge.votesessionmanagerapi.service.subject.exception

data class SubjectNotFoundException(private var idSubject: Long) :
    RuntimeException("Subject not found for this id=$idSubject")
