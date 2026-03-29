package com.zlrx.zalai.chat

import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import java.util.function.Function
import kotlin.random.Random

data class WeatherRequest(@ToolParam(description = "the place of weather") val location: String)
data class WeatherResponse(val temp: Double)

const val CURRENT_WEATHER_TOOL: String = "currentWeather"

@Configuration(proxyBeanMethods = false)
class DynamicTool {

    private val logger = LoggerFactory.getLogger(DynamicTool::class.java)




    @Bean(CURRENT_WEATHER_TOOL)
    @Description("Get the weather in location")
    fun currentWeather(): Function<WeatherRequest, WeatherResponse> = Function<WeatherRequest, WeatherResponse> {
        logger.info("Get weather for $it")
        WeatherResponse(Random.nextDouble(-20.0, 40.0))
    }

}