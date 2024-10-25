package apivision.dtos;

import apivision.entities.Adoption;
import apivision.entities.Dog;
import apivision.enums.AdoptionStatus;
import apivision.security.entitiess.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionDTO {
    private int id;
    private String username;
    private Dog dog;
    private LocalDateTime date;
    private AdoptionStatus status;

    public static AdoptionDTO toDTO(Adoption adoption) {
        return new AdoptionDTO(adoption.getId(), adoption.getUsername(), adoption.getDog(), adoption.getDate(), adoption.getStatus());
    }

    public static Adoption toEntity(AdoptionDTO adoptionDTO) {
        return new Adoption(adoptionDTO);
    }

    public static List<AdoptionDTO> toDTOList(List<Adoption> adoptionList) {
        return adoptionList.stream().map(AdoptionDTO::toDTO).collect(Collectors.toList());
    }
}
