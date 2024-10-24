package apivision.daos;

import apivision.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentDAOTest {

    private static final AppointmentDAO appointmentDAO = AppointmentDAO.getInstance(HibernateConfigState.TEST);
    private static Appointment a1, a2, a3;

    @BeforeAll
    void setUpAll() {
    }

    @BeforeEach
    void setUp() {
        createTestData();
        resetTableAndSequence();
    }

    private void createTestData() {
        a1 = Appointment.builder()
                .userId("123456789")
                .dog(Dog.builder().id(1).build())
                .date(LocalDateTime.now())
                .status(AppointmentStatus.PENDING)
                .build();
        a2 = Appointment.builder()
                .userId("987654321")
                .dog(Dog.builder().id(2).build())
                .date(LocalDateTime.now())
                .status(AppointmentStatus.DELIVERED)
                .build();
        a3 = Appointment.builder()
                .userId("123456987")
                .dog(Dog.builder().id(3).build())
                .date(LocalDateTime.now())
                .status(AppointmentStatus.PENDING)
                .build();
        appointmentDAO.create(a1);
        appointmentDAO.create(a2);
        appointmentDAO.create(a3);
    }

    private void resetTableAndSequence() {
        EntityManagerFactory emf = appointmentDAO.getEmf();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Appointment").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE appointment_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new JpaException("Error deleting appointments: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        appointmentDAO.close();
    }

    @Test
    void getInstance() {
        assertNotNull(appointmentDAO);
    }

    @Test
    void create() {
        Appointment a4 = Appointment.builder()
                .userId("121231234")
                .dog(Dog.builder().id(4).build())
                .date(LocalDateTime.now())
                .status(AppointmentStatus.PENDING)
                .build();
        appointmentDAO.create(a4);
        assertEquals(4, a4.getId());
    }

    @Test
    void findById() {
        Appointment actual = appointmentDAO.findById(a1.getId());
        assertEquals(a1, actual);
    }

    @Test
    void findByUserId() {
        Appointment actual = appointmentDAO.findByUserId(a1.getUserId());
        assertEquals(a1, actual);
    }

    @Test
    void update() {
        Appointment updated = Appointment.builder()
                .id(a1.getId())
                .userId(a1.getUserId())
                .dog(a1.getDog())
                .date(a1.getDate())
                .status(AppointmentStatus.DELIVERED)
                .build();
        assertNotEquals(a1.getStatus(), updated.getStatus());
        Appointment actual = appointmentDAO.update(updated);
        assertEquals(updated, actual);
        // Verify that the updated object has been persisted to the database
        Appointment persisted = appointmentDAO.findById(a1.getId());
        assertEquals(updated, persisted);
    }

    @Test
    void delete() {
        boolean actual = appointmentDAO.delete(a1);
        assertTrue(actual);
        actual = appointmentDAO.delete(a1);
        assertFalse(actual);
        Appointment deleted = appointmentDAO.findById(a1.getId());
        assertNull(deleted);
    }
}