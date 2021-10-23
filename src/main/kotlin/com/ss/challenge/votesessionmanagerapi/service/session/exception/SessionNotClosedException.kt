package com.ss.challenge.votesessionmanagerapi.service.session.exception

data class SessionNotClosedException(private var idSession: Long) :
    RuntimeException("Session is not closable, the time is not over for this id=$idSession")
