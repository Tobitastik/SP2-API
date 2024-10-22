package apivision.entities;

import apivision.dtos.AdoptionDTO;
import apivision.enums.AdoptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int petId;
    @Column(nullable = false)
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(name = "adoption_status", nullable = false)
    private AdoptionStatus status;

    public Adoption(AdoptionDTO adoptionDTO){
        this.id = adoptionDTO.getId();
        this.userId = adoptionDTO.getUserId();
        this.petId = adoptionDTO.getPetId();
        this.date = adoptionDTO.getDate();
        this.status = adoptionDTO.getStatus();
    }

}