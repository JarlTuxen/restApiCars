package dk.kea.restapicars.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private Set<Model> models;

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

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }
}
