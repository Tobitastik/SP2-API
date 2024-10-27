package apivision.dtos;

import apivision.entities.Adoption;
import apivision.entities.Appointment;
import apivision.entities.Dog;
import apivision.enums.DogStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class DogDTO {
    private int id;
    private String name;
    private String breed;
    private Integer age;
    private DogStatus status;
    private String description;
    private Adoption adoption;
    private Set<Appointment> appointments = new HashSet<>();


    // Static method to convert DTO to Entity
    public static Dog convertToEntity(DogDTO dogDTO) {
        return new Dog(dogDTO);
    }

    // Static method to convert Entity to DTO
    public static DogDTO convertToDTO(Dog dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getBreed(),
                dog.getAge(),
                dog.getStatus(),
                dog.getDescription(),
                dog.getAdoption(),
                dog.getAppointments()

        );
    }
    public DogDTO(int id, String name, String breed, Integer age, DogStatus status, String description) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.status = status;
        this.description = description;
    }

}
