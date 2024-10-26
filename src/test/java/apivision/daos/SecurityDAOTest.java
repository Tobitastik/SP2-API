package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.security.daos.SecurityDAO;
import apivision.security.entitiess.User;
import apivision.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityDAOTest {

    private static SecurityDAO securityDAO;
    private User user;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        securityDAO = new SecurityDAO(emf);

        // Create a test user if it does not already exist
        user = new User("testuser", "password123");
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            // Only create the user if it does not already exist
            if (em.find(User.class, user.getUsername()) == null) {
                em.persist(user);
            }
            em.getTransaction().commit();
        }
    }

    @Test
    void getVerifiedUser_ValidCredentials_ReturnsUserDTO() throws ValidationException {
        UserDTO verifiedUser = securityDAO.getVerifiedUser("testuser", "password123");
        assertNotNull(verifiedUser);
        assertEquals("testuser", verifiedUser.getUsername());
    }

    @Test
    void getVerifiedUser_InvalidPassword_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            securityDAO.getVerifiedUser("testuser", "wrongpassword");
        });
    }

   /* @Test
    void createUser_UserAlreadyExists_ThrowsEntityExistsException() {
        assertThrows(EntityExistsException.class, () -> {
            securityDAO.createUser("testuser", "password123");
        });
    }*/

    @Test
    void addRole_ValidUserAndRole_AddsRoleToUser() {
        // Create UserDTO from the existing user
        UserDTO userDTO = new UserDTO("testuser", Set.of("USER"));

        // Call addRole with the userDTO and the new role
        User updatedUser = securityDAO.addRole(userDTO, "admin");

        // Verify the role was added
        assertNotNull(updatedUser);
        assertTrue(updatedUser.getRoles().stream().anyMatch(role -> role.getRoleName().equals("admin")));
    }


    @Test
    void addRole_UserNotFound_ThrowsEntityNotFoundException() {
        UserDTO userDTO = new UserDTO("nonexistent", Set.of());
        assertThrows(EntityNotFoundException.class, () -> {
            securityDAO.addRole(userDTO, "admin");
        });
    }
}
