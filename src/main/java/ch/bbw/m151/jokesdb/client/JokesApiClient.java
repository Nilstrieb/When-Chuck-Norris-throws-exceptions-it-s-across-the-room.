package ch.bbw.m151.jokesdb.client;

import org.springframework.web.reactive.function.client.WebClient;

public class JokesApiClient {
    private final WebClient client;

    public JokesApiClient() {
        this.client = WebClient.create("https://v2.jokeapi.dev");
    }

    public Joke fetchJokes() {
        return this.client.get().uri("/joke/Programming").retrieve().bodyToMono(Joke.class).block();
    }
}
