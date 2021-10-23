package com.ss.challenge.votesessionmanagerapi.config.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import java.util.*

class UrlDecoderRequestInterceptor : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        requestTemplate.uri(requestTemplate.path().replace("%3A", ":"))
        val queries = requestTemplate.queries()
        if (queries.containsKey("date")) {
            val date: Collection<String> =
                listOf(
                    queries["date"].toString()
                        .replace("%3A", ":")
                        .replace("[", "")
                        .replace("]", "")
                )
            requestTemplate.query("date", Collections.emptyList())
            requestTemplate.query("date", date)
        }
    }
}
