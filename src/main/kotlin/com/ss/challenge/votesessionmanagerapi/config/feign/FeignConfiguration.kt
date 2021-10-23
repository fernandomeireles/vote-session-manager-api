package com.ss.challenge.votesessionmanagerapi.config.feign

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {
    @Bean
    fun urlEncoderRequestInterceptor(): UrlDecoderRequestInterceptor {
        return UrlDecoderRequestInterceptor()
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
