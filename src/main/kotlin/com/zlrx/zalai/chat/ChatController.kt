package com.zlrx.zalai.chat

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path =["/chat"], version = "3")
class ChatController(
    private val chatClient: ChatClient
) {

    @GetMapping
    fun generation(): String? {
        return this.chatClient.prompt()
            .user("What is 42?")
            .call()
            .content()
    }

}