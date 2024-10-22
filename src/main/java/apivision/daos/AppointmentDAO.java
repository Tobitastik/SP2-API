package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO implements IDAO<Appointment> {

    private static EntityManagerFactory emf;

    public AppointmentDAO() {
        emf = HibernateConfig.getEntityManagerFactory("petadoption");
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointmentList = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
            return query.getResultList();
        }
    }

    @Override
    public Appointment getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Appointment.class, id);
        }
    }

    @Override
    public void save(Appointment entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        }
    }

    @Override
    public void update(Appointment entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Appointment appointment = em.find(Appointment.class, id);
            em.remove(appointment);
            transaction.commit();
        }
    }
}