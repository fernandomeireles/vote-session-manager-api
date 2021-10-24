package com.ss.challenge.votesessionmanagerapi.utils.kafka

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.avro.Schema
import vote.result.ResultVoteSession

class CustomDeserializer : KafkaAvroDeserializer() {

    override fun deserialize(topic: String, bytes: ByteArray): Any {
        if (topic == "kafka.topics.result-vote-session") {
            this.schemaRegistry = getMockClient(ResultVoteSession.`SCHEMA$`)
        }
        return super.deserialize(topic, bytes)
    }

    private fun getMockClient(`schema$`: Schema?): SchemaRegistryClient {
        return object : MockSchemaRegistryClient() {
            @Synchronized
            override fun getById(id: Int): Schema? {
                return `schema$`
            }
        }
    }
}
