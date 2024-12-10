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
import jakarta.persistence.NoResultException;

import java.time.LocalDate;

public class Populator {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        SecurityDAO securityDAO = new SecurityDAO(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Check if role exists, otherwise create it
            Role adminRole = findOrCreateRole(em, "ADMIN");

            // Check if user exists, otherwise create it
            User user = findOrCreateUser(em, "testuser", "password123"); // Password will be hashed
            user.addRole(adminRole); // Add role to user

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
        }
    }

    private static Role findOrCreateRole(EntityManager em, String roleName) {
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            Role newRole = new Role(roleName);
            em.persist(newRole);
            return newRole;
        }
    }

    private static User findOrCreateUser(EntityManager em, String username, String password) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            User newUser = new User(username, password); // Ensure password is hashed as needed
            em.persist(newUser);
            return newUser;
        }
    }
}
