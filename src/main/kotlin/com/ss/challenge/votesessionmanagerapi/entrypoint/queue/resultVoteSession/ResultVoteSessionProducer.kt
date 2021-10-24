package com.ss.challenge.votesessionmanagerapi.entrypoint.queue.resultVoteSession

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import vote.result.ResultVoteSession

@Component
class ResultVoteSessionProducer(
    private val kafkaTemplate: KafkaTemplate<String, ResultVoteSession>,
    @Value("\${spring.kafka.topics.result-vote-session}")
    private val topic: String
) {

    fun post(result: ResultVoteSession) {

        try {
            kafkaTemplate.send(
                topic,
                result.idSession.toString(),
                result
            )
        } catch (e: Exception) {
            logger.error("Kafka error: ", e)
        }

        logger.info("topic=$topic session=${result.idSession} message=$result")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ResultVoteSessionProducer::class.java)
    }
}
