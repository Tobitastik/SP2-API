package apivision.dtos;

import apivision.entities.Adoption;
import apivision.enums.AdoptionStatus;
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
    private int userId;
    private int petId;
    private LocalDateTime date;
    private AdoptionStatus status;


    public static AdoptionDTO toDTO(Adoption adoption) {
        return new AdoptionDTO(adoption.getId(), adoption.getUserId(), adoption.getPetId(), adoption.getDate(), adoption.getStatus());
    }

    public static Adoption toEntity(AdoptionDTO adoptionDTO) {
        return new Adoption(adoptionDTO);
    }

    public static List<AdoptionDTO> toDTOList(List<Adoption> adoptionList) {
        return adoptionList.stream().map(AdoptionDTO::toDTO).collect(Collectors.toList());
    }
}