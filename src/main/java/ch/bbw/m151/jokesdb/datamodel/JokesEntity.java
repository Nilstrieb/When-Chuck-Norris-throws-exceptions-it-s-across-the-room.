package ch.bbw.m151.jokesdb.datamodel;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

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

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

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
