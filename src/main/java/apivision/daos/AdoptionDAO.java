package apivision.daos;

import apivision.dtos.AdoptionDTO;
import apivision.entities.Adoption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<AdoptionDTO> readAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            List<Adoption> adoptions = entityManager.createQuery("SELECT a FROM Adoption a", Adoption.class).getResultList();
            List<AdoptionDTO> adoptionDTOS = adoptions.stream().map(AdoptionDTO::toDTO).collect(Collectors.toList());
            return adoptionDTOS;
        }
    }

    public Adoption read(int id) {
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

    public Adoption getByUsernameAndId(String username, int id) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Adoption> query = em.createQuery("SELECT a FROM Adoption a WHERE a.username = :username AND a.id = :id", Adoption.class);
            query.setParameter("username", username);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
    }
}