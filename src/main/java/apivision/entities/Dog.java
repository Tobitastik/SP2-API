package apivision.entities;

import apivision.dtos.DogDTO;
import apivision.enums.DogStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DogStatus status;

    @Column(length = 500)
    private String description;


    public Dog(DogDTO dogDTO) {
        this.id = dogDTO.getId();
        this.name = dogDTO.getName();
        this.breed = dogDTO.getBreed();
        this.age = dogDTO.getAge();
        this.status = dogDTO.getStatus();
        this.description = dogDTO.getDescription();
    }
}