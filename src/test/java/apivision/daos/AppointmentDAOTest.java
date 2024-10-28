package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import apivision.entities.Dog;
import apivision.enums.AppointmentStatus;
import apivision.enums.DogStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
/*
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AppointmentDAOTest {
    private final static AppointmentDAO appointmentDAO = AppointmentDAO.getInstance(HibernateConfig.getEntityManagerFactoryForTest());
    private Appointment a1, a2, a3, a4;
    private Dog dog1, dog2, dog3, dog4;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Appointment").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE appointment_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }


        dog1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
        dog2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
        dog3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.AVAILABLE, "Gentle");

        a1 = new Appointment(0, "Test User 1", dog1, LocalDate.now(), AppointmentStatus.SCHEDULED);
        a2 = new Appointment(0, "Test User 2", dog2, LocalDate.now().plusDays(1), AppointmentStatus.SCHEDULED);
        a3 = new Appointment(0, "Test User 3", dog3, LocalDate.now().plusDays(2), AppointmentStatus.SCHEDULED);

        // Persist the test data
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.getTransaction().commit();
        }
    }

    @Test
    void getInstance() {
        assertNotNull(appointmentDAO);
    }

    @Test
    void create(){
        dog4 = new Dog(0, "Niels", "Rough Collie", 3, DogStatus.AVAILABLE, "Friendly and playful");
        Appointment a4 = Appointment.builder()
                .id(0)
                .username("Test User 4")
                .dog(dog4)
                .date(LocalDate.now())
                .status(AppointmentStatus.SCHEDULED)
                .build();
        appointmentDAO.create(a4);
        assertEquals(4, a4.getId());
    }

    @Test
    void findById() { //Test failing even though the expected and actual values are the same
        Appointment actual = appointmentDAO.read(a1.getId());
        assertEquals(a1 , actual);
    }
@Disabled
    @Test
    void update() {
        Appointment updated = Appointment.builder()
                .id(a1.getId())
                .username(a1.getUsername())
                .dog(a1.getDog())
                .date(a1.getDate())
                .status(AppointmentStatus.CANCELLED)
                .build();
        assertNotEquals(a1.getStatus(), updated.getStatus());
        Appointment actual = appointmentDAO.update(updated);
        // This needs an equals method in the Package class to work
        assertEquals(updated, actual);
        Appointment actual2 = appointmentDAO.read(a1.getId());
        System.out.println("a1: "+a1.getStatus()+" actual2: "+actual2.getStatus());
        assertEquals(AppointmentStatus.CANCELLED, actual2.getStatus());
    }
@Disabled
    @Test
    void delete() {
        int id = a1.getId(); // Replace with the ID of the appointment you want to delete

        // Remove the appointment from the dog's appointments set before deleting
        try (EntityManager em = HibernateConfig.getEntityManagerFactoryForTest().createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, a1.getDog().getId());
            dog.getAppointments().remove(a1);
            em.getTransaction().commit();
        }

        // Now delete the appointment
        appointmentDAO.delete(id);
        assertNull(appointmentDAO.read(id)); // Check if the appointment is deleted
    }

}
*/