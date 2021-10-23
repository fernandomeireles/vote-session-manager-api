package com.ss.challenge.votesessionmanagerapi.service.vote.exception

data class VoteInvalidException(private var exception: String) :
    RuntimeException("Vote not valid, $exception")
