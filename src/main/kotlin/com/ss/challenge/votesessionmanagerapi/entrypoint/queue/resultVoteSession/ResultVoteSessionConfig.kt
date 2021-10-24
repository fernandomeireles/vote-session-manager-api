package com.ss.challenge.votesessionmanagerapi.entrypoint.queue.resultVoteSession

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.util.backoff.FixedBackOff
import vote.result.ResultVoteSession
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
@Profile("!test")
class ResultVoteSessionConfig {
    @Bean(KAFKA_LISTENER_CONTAINER_FACTORY)
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<Long, ResultVoteSession>
    ):
        KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long?, ResultVoteSession?>>? {
        val factory = ConcurrentKafkaListenerContainerFactory<Long, ResultVoteSession>()
        factory.consumerFactory = consumerFactory
        factory.setAutoStartup(true)
        val backOffPolicy = ExponentialBackOffPolicy()
        backOffPolicy.initialInterval = Duration.ofSeconds(1).toMillis()
        backOffPolicy.maxInterval = Duration.ofSeconds(5).toMillis()

        val retryPolicy = SimpleRetryPolicy()
        retryPolicy.maxAttempts = 3

        val retryTemplate = RetryTemplate()
        retryTemplate.setRetryPolicy(retryPolicy)
        retryTemplate.setBackOffPolicy(backOffPolicy)
        factory.setRetryTemplate(retryTemplate)
        factory.setErrorHandler(
            SeekToCurrentErrorHandler(
                FixedBackOff(
                    TimeUnit.SECONDS.toMillis(5),
                    Long.MAX_VALUE
                )
            )
        )
        return factory
    }

    companion object {
        const val KAFKA_LISTENER_CONTAINER_FACTORY: String =
            "resultVoteSessionKafkaListenerContainerFactory"
        const val KAFKA_LISTENER_RESULT_VOTE_SESSION: String =
            "kafka.listener-result-vote-session"
    }
}
