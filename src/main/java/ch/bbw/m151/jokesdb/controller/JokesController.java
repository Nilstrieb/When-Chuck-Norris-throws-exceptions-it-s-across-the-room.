package ch.bbw.m151.jokesdb.controller;

import ch.bbw.m151.jokesdb.datamodel.JokeDto;
import ch.bbw.m151.jokesdb.service.JokesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JokesController {

    private final JokesService jokesService;

    @GetMapping("/joke")
    public JokeDto getJoke() {
        return jokesService.getJokeCached();
    }

    @PostMapping("/joke/{jokeId}/rate")
    public ResponseEntity<String> rateJoke(@RequestParam int rating, @PathVariable int jokeId) {
        if (rating < 0 || rating > 5) {
            return ResponseEntity.badRequest().body("Rating must be in range 0-5");
        }

        if (this.jokesService.addRating(jokeId, rating).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Successfully rated joke");
    }
}
