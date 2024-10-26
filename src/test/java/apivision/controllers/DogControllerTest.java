package apivision.controllers;

import apivision.config.ApplicationConfig;
import apivision.config.HibernateConfig;
import apivision.config.Populator;
import apivision.dtos.DogDTO;
import apivision.enums.DogStatus;
import apivision.security.controllers.SecurityController;
import apivision.security.daos.SecurityDAO;
import apivision.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
/*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DogControllerTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);
    private static Javalin app;
    private static String userToken, adminToken;
    private static UserDTO userDTO, adminDTO;
    private static final String BASE_URL = "http://localhost:7070/api/dogs";

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setIsTest(true);
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        System.out.println("Populating database with dogs");
        Populator.populateDogs(emf);

        UserDTO[] users = Populator.populateUsers(emf);
        userDTO = users[0];  // Regular user
        adminDTO = users[1]; // Admin user

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        } catch (ValidationException e) {
            System.err.println("Failed to verify user: " + e.getMessage());
            Assertions.fail("User verification failed: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();


            // Then delete from other tables
            em.createQuery("DELETE FROM Appointment").executeUpdate();
            em.createQuery("DELETE FROM Adoption").executeUpdate();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate(); // Optional: Clear roles if necessary

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void readAllDogs() {
        List<DogDTO> dogDTOs =
                given()
                        .when()
                        .header("Authorization", userToken)
                        .get(BASE_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(new TypeRef<List<DogDTO>>() {});

        System.out.println("Number of dogs retrieved: " + dogDTOs.size());
        assertThat(dogDTOs.size(), is(3));
        assertThat(dogDTOs.get(0).getName(), is("Buddy"));
        assertThat(dogDTOs.get(1).getName(), is("Max"));
        assertThat(dogDTOs.get(2).getName(), is("Bella"));
    }

    @Test
    void readDogById() {
        DogDTO dogDTO =
                given()
                        .when()
                        .header("Authorization", userToken)
                        .get(BASE_URL + "/1")
                        .then()
                        .statusCode(200)
                        .log().ifError()
                        .extract()
                        .as(DogDTO.class);

        assertThat(dogDTO.getName(), is("Buddy"));
        assertThat(dogDTO.getBreed(), is("Golden Retriever"));
        assertThat(dogDTO.getAge(), is(3));
    }

    @Test
    void createDog() {
        DogDTO newDog = new DogDTO(0, "Rocky", "Bulldog", 4, DogStatus.AVAILABLE, "Energetic and brave");

        DogDTO createdDog =
                given()
                        .when()
                        .header("Authorization", adminToken)
                        .contentType("application/json")
                        .body(newDog)
                        .post(BASE_URL)
                        .then()
                        .statusCode(201)
                        .extract()
                        .as(DogDTO.class);

        assertThat(createdDog.getName(), is("Rocky"));
    }

    @Test
    void updateDog() {
        DogDTO updatedDog = new DogDTO(1, "Buddy Updated", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");

        DogDTO returnedDog =
                given()
                        .when()
                        .header("Authorization", adminToken)
                        .contentType("application/json")
                        .body(updatedDog)
                        .put(BASE_URL + "/1")
                        .then()
                        .log().ifError()
                        .statusCode(200)
                        .extract()
                        .as(DogDTO.class);

        assertThat(returnedDog.getName(), is("Buddy Updated"));
    }

    @Test
    void deleteDog() {
        given()
                .when()
                .header("Authorization", adminToken)
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }
}
*/