package apivision.dtos;

import apivision.entities.Adoption;
import apivision.enums.AdoptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter

public class AdoptionDTO {
    private final int id;
    private final int userId;
    private final int petId;
    @Setter
    private LocalDateTime date;
    @Setter
    private AdoptionStatus status;

    public AdoptionDTO(Adoption adoption) {
        this.id = adoption.getId();
        this.userId = adoption.getUserId();
        this.petId = adoption.getPetId();
        this.date = adoption.getDate();
        this.status = adoption.getStatus();
    }

    // Add static method to convert a list of Adoption entities to a list of AdoptionDTOs
    public static List<AdoptionDTO> toDTOList(List<Adoption> adoptions) {
        return adoptions.stream().map(AdoptionDTO::new).collect(Collectors.toList());
    }
}