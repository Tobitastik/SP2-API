package apivision.dtos;

import apivision.entities.Dog;
import apivision.enums.DogStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogDTO {
    private int id;
    private String name;
    private String breed;
    private Integer age;
    private DogStatus status;
    private String description;


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
                dog.getDescription()
        );
    }
}
