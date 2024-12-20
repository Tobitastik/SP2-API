package apivision.daos;

import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import apivision.entities.Dog;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppointmentDAO {

    private static AppointmentDAO instance;
    private static EntityManagerFactory emf;

    public static AppointmentDAO getInstance(EntityManagerFactory _emf) {
        if(instance == null) {
            emf = _emf;
            instance = new AppointmentDAO();
        }
        return instance;
    }

    public List<AppointmentDTO> readAll() {
        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
            List<Appointment> appointmentList = query.getResultList();

            // Convert each Appointment entity to AppointmentDTO
            for (Appointment appointment : appointmentList) {
                appointmentDTOList.add(AppointmentDTO.toDTO(appointment));
            }
        }
        return appointmentDTOList;
    }


    public Appointment read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Appointment.class, integer);
        }
    }

    public void create(Appointment entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            //System.out.println("Persisting Appointment: " + entity);
            //System.out.println("Associated Dog: " + entity.getDog());

            Dog managedDog = em.merge(entity.getDog());
            entity.setDog(managedDog);

            em.persist(entity); // Persist the Appointment

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception
            throw e; // Rethrow the exception for further handling
        }
    }


    public Appointment update(Appointment entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        }
        return entity;
    }

    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(em.find(Appointment.class, integer));
            transaction.commit();
        }
    }


    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Appointment appointment = em.find(Appointment.class, integer);
            return appointment != null;
        }
    }
}