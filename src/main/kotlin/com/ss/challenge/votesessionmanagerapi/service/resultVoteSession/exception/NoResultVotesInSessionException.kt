package com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.exception

data class NoResultVotesInSessionException(private var idSession: Long) :
    RuntimeException("No votes in session, for this id=$idSession")
