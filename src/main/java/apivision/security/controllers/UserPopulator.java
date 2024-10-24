package apivision.security.controllers;

import apivision.config.HibernateConfig;
import apivision.security.daos.SecurityDAO;
import apivision.security.entitiess.Role;
import apivision.security.entitiess.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserPopulator {

    public static void main(String[] args) {
        // Use the HibernateConfig to get the EntityManagerFactory
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption"); // Replace with actual DB name
        SecurityDAO securityDAO = new SecurityDAO(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Create a new user and a role
            User user = new User("testuser", "password123"); // Password will be hashed
            Role userRole = new Role("USER");

            // Add role to user
            user.addRole(userRole);

            // Persist role and user in the database
            em.persist(userRole); // Persist the role first
            em.persist(user);

            em.getTransaction().commit();
            System.out.println("User and role successfully added to the database.");
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
