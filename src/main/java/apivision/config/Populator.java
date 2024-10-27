package apivision.config;

import apivision.entities.*;
import apivision.enums.AdoptionStatus;
import apivision.enums.AppointmentStatus;
import apivision.enums.DogStatus;
import apivision.security.daos.SecurityDAO;
import apivision.security.entitiess.Role;
import apivision.security.entitiess.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Populator {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        SecurityDAO securityDAO = new SecurityDAO(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Create and populate the user with a role
            User user = new User("testuser", "password123"); // Password will be hashed
            Role adminRole = new Role("ADMIN");

            user.addRole(adminRole);

            em.persist(adminRole); // Persist the role first
            em.persist(user);

            System.out.println("User and role successfully added to the database.");

            // Create and populate dogs
            Dog dog1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
            Dog dog2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
            Dog dog3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.ADOPTED, "Gentle and loving");

            em.persist(dog1);
            em.persist(dog2);
            em.persist(dog3);

            System.out.println("Dogs populated successfully!");

            // Create an adoption for dog3 (adopted dog)
            Adoption adoption = new Adoption(0, user.getUsername(), dog3, LocalDate.now(), AdoptionStatus.APPROVED);
            dog3.setAdoption(adoption);

            em.persist(adoption);

            // Create appointments for all dogs
            Appointment appointment1 = new Appointment(0, user.getUsername(), dog1, LocalDate.now().plusDays(1), AppointmentStatus.SCHEDULED);
            dog1.getAppointments().add(appointment1);
            em.persist(appointment1);

            Appointment appointment2 = new Appointment(0, user.getUsername(), dog2, LocalDate.now().plusDays(2), AppointmentStatus.SCHEDULED);
            dog2.getAppointments().add(appointment2);
            em.persist(appointment2);

            // Note: dog3 is adopted and won't have an appointment
            System.out.println("Appointments created for available dogs successfully!");

            // Commit the transaction
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
