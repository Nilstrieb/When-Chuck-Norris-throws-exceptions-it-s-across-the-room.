package ch.bbw.m151.jokesdb.service;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.stream.IntStream;

@SpringBootTest
class JokesServiceTest implements WithAssertions {
    @Autowired
    private JokesService service;

    @Test
    void jokeHasNoDuplicates() {
        var dtos = IntStream.range(0, 10).mapToObj(i -> service.getJokeCached()).toList();
        var deduplicatedDtos = new HashSet<>(dtos);

        assertThat(dtos).size().isEqualTo(deduplicatedDtos.size());
    }
}