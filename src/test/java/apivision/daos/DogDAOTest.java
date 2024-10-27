package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.entities.Dog;
import apivision.dtos.DogDTO;
import apivision.enums.DogStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DogDAOTest {

    private static final DogDAO dogDAO = DogDAO.getInstance(HibernateConfig.getEntityManagerFactoryForTest());
    private Dog d1, d2, d3;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = dogDAO.getEmf();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dog").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE dog_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }

        // Create test data
        d1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
        d2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
        d3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.ADOPTED, "Gentle and loving");

        // Persis dogs
        d1.setId(dogDAO.create(DogDTO.convertToDTO(d1)).getId());
        d2.setId(dogDAO.create(DogDTO.convertToDTO(d2)).getId());
        d3.setId(dogDAO.create(DogDTO.convertToDTO(d3)).getId());
    }

    @Test
    void getInstance() {
        assertNotNull(dogDAO);
    }

    @Test
    void create() {
        Dog d4 = new Dog(0, "Charlie", "Beagle", 4, DogStatus.AVAILABLE, "Curious and merry");
        DogDTO dto = DogDTO.convertToDTO(d4);
        DogDTO createdDog = dogDAO.create(dto);
        assertNotNull(createdDog.getId());
        assertEquals("Charlie", createdDog.getName());
    }

    @Test
    void findById() {
        DogDTO actual = dogDAO.read(d1.getId());
        assertEquals(d1.getName(), actual.getName());
    }

    @Test
    void update() {
        DogDTO updatedDTO = new DogDTO(d1.getId(), "Buddy", "Golden Retriever", 4, DogStatus.AVAILABLE, "Playful and friendly");
        DogDTO actual = dogDAO.update(d1.getId(), updatedDTO);

        assertNotNull(actual, "Expected non-null DogDTO after update");
        assertEquals(updatedDTO.getAge(), actual.getAge(), "Expected age to be updated");
    }

    @Test
    void delete() {
        dogDAO.delete(d1.getId());
        assertNull(dogDAO.read(d1.getId()));
    }
}
