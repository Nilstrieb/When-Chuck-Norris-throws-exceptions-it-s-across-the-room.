package ch.bbw.m151.jokesdb;

import ch.bbw.m151.jokesdb.service.JokesApiClientService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class ClientTest implements WithAssertions {
    @Test
    void fetchOneJoke() {
        var client = new JokesApiClientService();
        var joke = client.fetchJokes();
        assertThat(joke).isNotNull();
        System.out.println(joke);
    }
}
