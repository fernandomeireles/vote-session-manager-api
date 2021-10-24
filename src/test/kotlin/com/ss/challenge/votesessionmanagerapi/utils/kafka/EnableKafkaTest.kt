package com.ss.challenge.votesessionmanagerapi.utils.kafka

import com.ss.challenge.votesessionmanagerapi.entrypoint.queue.resultVoteSession.ResultVoteSessionConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.retry.backoff.ExponentialBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.util.backoff.FixedBackOff
import vote.result.ResultVoteSession
import java.time.Duration
import java.util.concurrent.TimeUnit

@EnableKafka
class EnableKafkaTest {

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Autowired
    private lateinit var broker: EmbeddedKafkaBroker

    @Bean
    fun consumerFactory(): ConsumerFactory<Long, GenericRecord> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = broker.brokersAsString
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = LongDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = CustomDeserializer::class.java
        props[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        props["schema.registry.url"] = "unused"
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean(ResultVoteSessionConfig.KAFKA_LISTENER_CONTAINER_FACTORY)
    fun clientAdmissionKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<Long, ResultVoteSession> {
        val factory = ConcurrentKafkaListenerContainerFactory<Long, ResultVoteSession>()
        factory.consumerFactory = consumerFactory()
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
}
