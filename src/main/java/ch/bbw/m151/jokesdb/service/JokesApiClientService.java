package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokeApiDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JokesApiClientService {
    private final WebClient client;

    public JokesApiClientService() {
        this.client = WebClient.create("https://v2.jokeapi.dev");
    }

    public JokeApiDto fetchJoke() {
        return this.client.get().uri("/joke/Programming?type=single").retrieve().bodyToMono(JokeApiDto.class).block();
    }
}
