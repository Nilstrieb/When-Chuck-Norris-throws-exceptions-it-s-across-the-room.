package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokeDto;
import org.springframework.web.reactive.function.client.WebClient;

public class JokesApiClientService {
    private final WebClient client;

    public JokesApiClientService() {
        this.client = WebClient.create("https://v2.jokeapi.dev");
    }

    public JokeDto fetchJokes() {
        return this.client.get().uri("/joke/Programming").retrieve().bodyToMono(JokeDto.class).block();
    }
}
