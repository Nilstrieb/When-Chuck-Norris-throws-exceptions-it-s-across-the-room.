package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokeDto;
import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class JokesService {

    private static final Logger log = LoggerFactory.getLogger(JokesService.class);

    private final JokesRepository jokesRepository;

    private final JokesApiClientService apiClientService;

    public JokesService(JokesRepository jokesRepository, JokesApiClientService apiClientService) {
        this.jokesRepository = jokesRepository;
        this.apiClientService = apiClientService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void preloadDatabase() {
        if (jokesRepository.count() != 0) {
            log.info("database already contains data...");
            return;
        }
        log.info("will load jokes from classpath...");
        try (var lineStream = Files.lines(new ClassPathResource("chucknorris.txt").getFile().toPath(), StandardCharsets.UTF_8)) {
            var jokes = lineStream.filter(x -> !x.isEmpty()).map(x -> new JokesEntity().setJoke(x)).toList();
            jokesRepository.saveAll(jokes);
        } catch (IOException e) {
            throw new RuntimeException("failed reading jokes from classpath", e);
        }
    }

    public JokeDto getJokeCached() {
        var newJoke = this.apiClientService.fetchJoke();

        while (this.jokesRepository.findById(newJoke.getId()).isPresent()) {
            newJoke = this.apiClientService.fetchJoke();
        }

        var jokeEntity = new JokesEntity().setId(newJoke.getId()).setJoke(newJoke.getJoke());
        this.jokesRepository.save(jokeEntity);

        return new JokeDto().setJoke(jokeEntity.getJoke()).setJokeId(jokeEntity.getId());
    }

    public Optional<Void> addRating(int jokeId, int rating) {
        return this.jokesRepository.findById(jokeId).map(oldJoke -> {
            // guard against overflow
            var ratings = Integer.max(oldJoke.getTotalRatings() + rating, oldJoke.getTotalRatings());
            var newJoke = oldJoke.setTotalRatings(ratings);
            // this has a race condition actually
            this.jokesRepository.save(newJoke);
            return null;
        });
    }
}
