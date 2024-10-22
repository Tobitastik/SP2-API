package apivision.entities;

import apivision.enums.AdoptionStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Adoption {

    private int id;
    private int userId;
    private int petId;
    private LocalDateTime date;
    private AdoptionStatus status;

}
