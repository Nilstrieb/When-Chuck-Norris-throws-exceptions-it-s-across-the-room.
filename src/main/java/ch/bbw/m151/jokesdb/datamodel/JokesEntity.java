package ch.bbw.m151.jokesdb.datamodel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "jokes")
public class JokesEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    int id;

    @Column(nullable = false)
    @Getter
    @Setter
    String joke;

    @Version
    private int version;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
