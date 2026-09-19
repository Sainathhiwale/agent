package com.examen.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HelloTimeAgent {

    public static BaseAgent ROOT_AGENT = initAgent();

    private static BaseAgent initAgent() {

        return LlmAgent.builder()
                .name("hello-time-agent")

                .description(
                        "Tells the current time in a specified city"
                )

                .instruction("""
                        You are a helpful assistant that tells
                        the current time in a city.

                        Use the getCurrentTime tool
                        when the user asks for the time.
                        """)

                .model("gemini-flash-latest")

                .tools(
                        FunctionTool.create(
                                HelloTimeAgent.class,
                                "getCurrentTime"
                        )
                )

                .build();
    }

    /*@Schema(
            description = "Get the current time for a given city"
    )
    public static Map<String, String> getCurrentTime(

            @Schema(
                    name = "city",
                    description = "Name of the city"
            )
            String city
    ) {

        return Map.of(
                "city", city,
                "time", "10:30 AM"
        );
    }*/
    @Schema(
            description = "Get the current time for a given city"
    )
    public static Map<String, String> getCurrentTime(

            @Schema(
                    name = "city",
                    description = "Name of the city"
            )
            String city
    ) {

        ZoneId zoneId;

        switch (city.toLowerCase()) {
            case "pune":
            case "mumbai":
            case "delhi":
            case "bangalore":
            case "hyderabad":
            case "kolkata":
            case "chennai":
                zoneId = ZoneId.of("Asia/Kolkata");
                break;

            case "london":
                zoneId = ZoneId.of("Europe/London");
                break;

            case "new york":
                zoneId = ZoneId.of("America/New_York");
                break;

            case "tokyo":
                zoneId = ZoneId.of("Asia/Tokyo");
                break;

            default:
                return Map.of(
                        "city", city,
                        "error", "Unsupported city"
                );
        }

        String currentTime = ZonedDateTime
                .now(zoneId)
                .format(DateTimeFormatter.ofPattern("hh:mm:ss a"));

        return Map.of(
                "city", city,
                "time", currentTime
        );
    }
}