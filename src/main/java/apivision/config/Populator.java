package apivision.config;


import apivision.config.HibernateConfig;
import apivision.entities.Dog;
import apivision.enums.DogStatus;
import apivision.security.daos.SecurityDAO;
import apivision.security.entitiess.Role;
import apivision.security.entitiess.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Populator {

    public static void main(String[] args) {
        // Use the HibernateConfig to get the EntityManagerFactory
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption"); // Replace with actual DB name
        SecurityDAO securityDAO = new SecurityDAO(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Create and populate the user with a role
            User user = new User("testuser", "password123"); // Password will be hashed
            Role adminRole = new Role("ADMIN");

            // Add role to user
            user.addRole(adminRole);

            // Persist role and user in the database
            em.persist(adminRole); // Persist the role first
            em.persist(user);

            System.out.println("User and role successfully added to the database.");

            // Create and populate dogs
            Dog dog1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
            Dog dog2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
            Dog dog3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.ADOPTED, "Gentle and loving");
            Dog dog4 = new Dog(0, "Charlie", "Beagle", 4, DogStatus.AVAILABLE, "Curious and merry");
            Dog dog5 = new Dog(0, "Lucy", "Bulldog", 6, DogStatus.AVAILABLE, "Calm and courageous");

            // Persist dogs in the database
            em.persist(dog1);
            em.persist(dog2);
            em.persist(dog3);
            em.persist(dog4);
            em.persist(dog5);

            System.out.println("Dogs populated successfully!");

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
