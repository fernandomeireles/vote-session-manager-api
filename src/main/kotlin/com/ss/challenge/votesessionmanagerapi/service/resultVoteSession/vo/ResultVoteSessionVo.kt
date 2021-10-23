package com.ss.challenge.votesessionmanagerapi.service.resultVoteSession.vo

import com.ss.challenge.votesessionmanagerapi.entity.resultVoteSession.ResultStatusEnum

data class ResultVoteSessionVo(
    val resultNegativesVotes: Long,
    val resultPositivesVotes: Long,
    val resultStatus: ResultStatusEnum
)
