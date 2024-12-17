package apivision.config;

import apivision.entities.*;
import apivision.enums.AdoptionStatus;
import apivision.enums.AppointmentStatus;
import apivision.enums.DogStatus;
import apivision.security.daos.SecurityDAO;
import apivision.security.entitiess.Role;
import apivision.security.entitiess.User;
import dk.bugelhartmann.UserDTO;
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

            User user2 = findOrCreateUser(em, "admin", "admin123"); // Password will be hashed
             // Add role to user
            user.addRole(adminRole);



            em.persist(user);
            em.persist(user2);

            System.out.println("User and role successfully added to the database.");

            // Create and populate dogs
            Dog dog1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
            Dog dog2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
            Dog dog3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.ADOPTED, "Gentle and loving");
            Dog dog4 = new Dog(0, "Charlie", "Bulldog", 4, DogStatus.AVAILABLE, "Calm and courageous");
            Dog dog5 = new Dog(0, "Rocky", "Boxer", 6, DogStatus.AVAILABLE, "Energetic and strong");
            Dog dog6 = new Dog(0, "Daisy", "Beagle", 1, DogStatus.ADOPTED, "Curious and cheerful");
            Dog dog7 = new Dog(0, "Molly", "Poodle", 3, DogStatus.AVAILABLE, "Intelligent and playful");
            Dog dog8 = new Dog(0, "Cooper", "Cocker Spaniel", 2, DogStatus.AVAILABLE, "Loving and social");
            Dog dog9 = new Dog(0, "Sadie", "Shih Tzu", 4, DogStatus.ADOPTED, "Affectionate and loyal");
            Dog dog10 = new Dog(0, "Ziggy", "Chihuahua", 5, DogStatus.AVAILABLE, "Small but fearless");
            Dog dog11 = new Dog(0, "Luna", "Siberian Husky", 2, DogStatus.AVAILABLE, "Adventurous and friendly");
            Dog dog12 = new Dog(0, "Jack", "Doberman Pinscher", 4, DogStatus.AVAILABLE, "Alert and confident");
            Dog dog13 = new Dog(0, "Rex", "Rottweiler", 3, DogStatus.ADOPTED, "Protective and loyal");
            Dog dog14 = new Dog(0, "Pepper", "Yorkshire Terrier", 1, DogStatus.AVAILABLE, "Playful and energetic");

            em.persist(dog1);
            em.persist(dog2);
            em.persist(dog3);
            em.persist(dog4);
            em.persist(dog5);
            em.persist(dog6);
            em.persist(dog7);
            em.persist(dog8);
            em.persist(dog9);
            em.persist(dog10);
            em.persist(dog11);
            em.persist(dog12);
            em.persist(dog13);
            em.persist(dog14);

            System.out.println("14 Dogs populated successfully!");


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
