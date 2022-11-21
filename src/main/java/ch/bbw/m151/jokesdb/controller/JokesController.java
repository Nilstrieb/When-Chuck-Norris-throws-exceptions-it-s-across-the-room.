package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.JokeDto;
import ch.bbw.m151.jokesdb.service.JokesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JokesController {

    private final JokesService jokesService;

    @GetMapping("/joke")
    public JokeDto getJoke() {
        return jokesService.getJokeCached();
    }
}
