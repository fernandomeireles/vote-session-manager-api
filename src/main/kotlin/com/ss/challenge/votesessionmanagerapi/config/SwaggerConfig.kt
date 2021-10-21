package com.ss.challenge.votesessionmanagerapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@EnableConfigurationProperties
class SwaggerConfig {

    @Value("\${swagger.message.welcome}")
    private val welcome: String? = null

    @Value("\${swagger.message.description}")
    private val description: String? = null

    @Value("\${swagger.message.version}")
    private val version: String? = null

    @Value("\${swagger.message.contact.name}")
    private val contactName: String? = null

    @Value("\${swagger.message.contact.email}")
    private val contactEmail: String? = null

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.any())
            .apis(RequestHandlerSelectors.basePackage("com.ss.challenge.votesessionmanagerapi.entrypoint"))
            .build()
            .apiInfo(generateApiInfo())
    }

    private fun generateApiInfo(): ApiInfo {
        return ApiInfoBuilder().title(welcome)
            .description(description)
            .version(version)
            .contact(Contact(contactName, "", contactEmail))
            .build()
    }
}
