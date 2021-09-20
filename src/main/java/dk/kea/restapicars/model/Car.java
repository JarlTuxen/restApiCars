package dk.kea.restapicars.model;

import javax.persistence.*;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
