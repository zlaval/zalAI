package com.zlrx.zalai.chat

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam

class NamingTool {


    @Tool(description = "Get random fantasy character name")
    fun characterName(): String =
        listOf("Drizzt", "Anezka", "Percival", "Sarevok", "Frodo", "Vin", "Kaladin", "Goku")
            .random()


    @Tool(description = "Get character name by index")
    fun characterNameByIndex(@ToolParam(description = "Number between 0 and 8 for index") index: Int): String =
        listOf("Wolfgar", "Wilhelm", "Lancelot", "Bhaal", "Aragorn", "Kelsier", "Dalinar", "Gohan").get(index)

}