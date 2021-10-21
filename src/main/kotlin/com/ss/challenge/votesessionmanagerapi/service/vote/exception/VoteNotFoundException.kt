package com.ss.challenge.votesessionmanagerapi.service.vote.exception

data class VoteNotFoundException(private var idVote: Long) :
    RuntimeException("Vote not found for this id=$idVote")
