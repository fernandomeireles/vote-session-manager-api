package com.ss.challenge.votesessionmanagerapi.service.session.exception

data class SessionNotFoundException(private var idSession: Long) :
    RuntimeException("Session not found for this id=$idSession")
