package com.examen.agent;

import com.google.adk.agents.RunConfig;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;

import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AgentCliRunner {

    public static void main(String[] args) {
        String apiKey = System.getenv("GOOGLE_API_KEY");

        System.out.println(
                "GOOGLE_API_KEY configured: " + (apiKey != null)
        );
        RunConfig runConfig =
                RunConfig.builder().build();

        InMemoryRunner runner =
                new InMemoryRunner(
                        HelloTimeAgent.ROOT_AGENT
                );

        Session session =
                runner
                        .sessionService()
                        .createSession(
                                runner.appName(),
                                "user1234"
                        )
                        .blockingGet();

        try (
                Scanner scanner =
                        new Scanner(
                                System.in,
                                StandardCharsets.UTF_8
                        )
        ) {

            while (true) {

                System.out.print("\nYou > ");

                String userInput =
                        scanner.nextLine();

                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userMessage =
                        Content.fromParts(
                                Part.fromText(userInput)
                        );

                Flowable<Event> events =
                        runner.runAsync(
                                session.userId(),
                                session.id(),
                                userMessage,
                                runConfig
                        );

                System.out.print("\nAgent > ");

                events.blockingForEach(event -> {

                    if (event.finalResponse()) {

                        System.out.println(
                                event.stringifyContent()
                        );
                    }

                });
            }
        }
    }
}