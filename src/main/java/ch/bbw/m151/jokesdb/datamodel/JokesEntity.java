package ch.bbw.m151.jokesdb.datamodel;

import javax.persistence.*;

@Entity
@Table(name = "jokes")
public class JokesEntity {

	@Id
	@GeneratedValue
	int id;

	@Column(nullable = false)
	String joke;

	@Version
	private int version;

	public JokesEntity setJoke(String joke) {
		this.joke = joke;
		return this;
	}

	public int getId() {
		return id;
	}

	public String getJoke() {
		return joke;
	}
}
