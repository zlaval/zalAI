package com.zlrx.zalai.chat

import org.springframework.ai.chat.client.AdvisorParams
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class ChatController(
    private val chatClient: ChatClient,
    private val vectorStore: VectorStore
) {

//    @PostConstruct
//    fun init() {
//        val documents = listOf(
//            Document(
//                "Zalai is the best software",
//                mapOf("name" to "zalai")
//            ),
//            Document("The World is Big and Salvation Lurks Around the Corner"),
//            Document(
//                "You walk forward facing the past and you turn back toward the future.",
//                mapOf("meta2" to "meta2")
//            )
//        )
//
//        vectorStore.add(documents)
//    }

    @GetMapping("/chat")
    fun chat(
        @RequestParam id: String,
        @RequestParam(required = true) prompt: String,
    ) = this.chatClient.prompt()
        .advisors {
            it.param(ChatMemory.CONVERSATION_ID, id)
        }
        .advisors(
            QuestionAnswerAdvisor.builder(vectorStore).build()
        )
        .tools(NamingTool())
        //.advisors {
        // SimpleLoggerAdvisor(
        //     { request -> "Custom request: " + request?.prompt()?.getUserMessage() },
        //     { response -> "Custom response: " + response?.getResult() },
        //     0
        // )
        //}
        .system("You are a helpful assistant")
        .user(prompt)
        .call()
        .content()


    @GetMapping
    fun generation(): ResponseEntity<ProducerCars> {
        val response = this.chatClient.prompt()
            .advisors {
                AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT
            }
            .system("You are a car specialist")
            .user {
                it.text("Generate a list of cars for a random car producer")
                    .metadata("id", 1)
            }
            .call()
            .entity(ProducerCars::class.java)
        //content()

        return ResponseEntity.ok(response)
    }

}