package apivision.entities;

import apivision.dtos.AdoptionDTO;
import apivision.enums.AdoptionStatus;
import apivision.security.entitiess.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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


    @JoinColumn(name = "username", nullable = false)
    private String username;

    @OneToOne
    @JoinColumn(name = "dog_id", nullable = false, unique = true)  // Use dog_id and make it unique
    private Dog dog;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "adoption_status", nullable = false)
    private AdoptionStatus status;

    public Adoption(AdoptionDTO adoptionDTO){
        this.id = adoptionDTO.getId();
        this.username = adoptionDTO.getUsername();
        this.dog = adoptionDTO.getDog();
        this.date = adoptionDTO.getDate();
        this.status = adoptionDTO.getStatus();
    }

}