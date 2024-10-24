package apivision.daos;

import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;


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
            em.persist(entity);
            transaction.commit();
        }
    }

    public void update(Appointment entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        }
    }

    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Appointment appointment = em.find(Appointment.class, id);
            em.remove(appointment);
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