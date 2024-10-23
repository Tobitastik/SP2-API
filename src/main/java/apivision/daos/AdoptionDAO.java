package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.dtos.AdoptionDTO;
import apivision.entities.Adoption;
import apivision.entities.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AdoptionDAO {

    private static AdoptionDAO instance;
    private static EntityManagerFactory emf;


    public static AdoptionDAO getInstance(EntityManagerFactory _emf) {
        if(instance == null) {
            emf = _emf;
            instance = new AdoptionDAO();
        }
        return instance;
    }

    public List<Adoption> getAll() {
        List<Adoption> adoptionList = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Adoption> query = em.createQuery("SELECT a FROM Adoption a", Adoption.class);
            return query.getResultList();
        }
    }

    public Adoption getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Adoption.class, id);
        }
    }

    public void create(Adoption entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        }
    }

    public void update(Adoption entity) {
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
            Adoption adoption = em.find(Adoption.class, id);
            em.remove(adoption);
            transaction.commit();
        }
    }

    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Adoption adoption = em.find(Adoption.class, integer);
            return adoption != null;
        }
    }

}