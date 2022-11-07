package ch.bbw.m151.jokesdb.client;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JokeApiResponse {
    private Boolean error;
    private List<Joke> jokes;
}
