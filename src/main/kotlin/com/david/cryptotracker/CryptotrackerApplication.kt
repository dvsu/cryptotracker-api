package com.david.cryptotracker

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CryptotrackerApplication {

	// Message converter of GCP PubSub integration
	@Bean
	fun jacksonPubSubMessageConverter(objectMapper: ObjectMapper) = JacksonPubSubMessageConverter(objectMapper)
}

fun main(args: Array<String>) {
	runApplication<CryptotrackerApplication>(*args)
}
