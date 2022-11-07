package ch.bbw.m151.jokesdb.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Joke {
    private String category;
    private String type;

    private String setup;
    private String delivery;

    private String joke;

    private Integer id;
    private Boolean safe;
    private String lang;
}
