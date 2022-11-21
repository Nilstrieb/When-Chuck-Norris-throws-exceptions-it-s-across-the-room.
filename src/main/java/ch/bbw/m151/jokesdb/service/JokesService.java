package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokeApiDto;
import ch.bbw.m151.jokesdb.datamodel.JokeDto;
import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

@Service
public class JokesService {

    private static final Logger log = LoggerFactory.getLogger(JokesService.class);

    private final Random random;
    private final JokesRepository jokesRepository;

    private final JokesApiClientService apiClientService;

    public JokesService(JokesRepository jokesRepository, JokesApiClientService apiClientService) {
        this.random = new Random();
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

    public JokeApiDto fetchJokeIntoDb() {
        JokeApiDto newJoke = this.apiClientService.fetchJoke();
        var jokeEntity = new JokesEntity().setId(newJoke.getId()).setJoke(newJoke.getJoke());
        this.jokesRepository.save(jokeEntity);
        return newJoke;
    }

    public JokeDto getJokeCached() {
        if (random.nextBoolean()) {
            var joke = this.jokesRepository.findOne(Example.of(new JokesEntity()));

            return joke.map(jokesEntity -> new JokeDto().setJoke(jokesEntity.getJoke()))
                    .orElseGet(() -> new JokeDto().setJoke(this.fetchJokeIntoDb().getJoke()));
        }

        return new JokeDto().setJoke(this.fetchJokeIntoDb().getJoke());
    }
}
