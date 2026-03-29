package com.zlrx.zalai.config

import com.zlrx.zalai.chat.CURRENT_WEATHER_TOOL
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.chat.memory.repository.redis.RedisChatMemoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPooled


@Configuration
//@AutoConfigureAfter(RedisChatMemoryAutoConfiguration::class)
class AiConfig {


    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder, memory: ChatMemory): ChatClient {
        return chatClientBuilder
            .defaultAdvisors(
                MessageChatMemoryAdvisor.builder(memory).build()
            )
            .defaultToolNames(CURRENT_WEATHER_TOOL)
            .build()
    }

    @Bean
    fun redisChatMemoryRepository(jedisClient: JedisPooled): RedisChatMemoryRepository {
        return RedisChatMemoryRepository.builder()
            .jedisClient(jedisClient)
            .build()
    }

    @Bean
    //@ConditionalOnBean(RedisChatMemoryRepository::class)
    fun chatMemory(chatMemoryRepository: RedisChatMemoryRepository): ChatMemory {
        val memory = MessageWindowChatMemory.builder()
            .chatMemoryRepository(chatMemoryRepository)
            .maxMessages(10)
            .build()
        return memory
    }
}