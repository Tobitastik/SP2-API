package apivision.daos;

import apivision.config.HibernateConfig;
import apivision.dtos.AdoptionDTO;
import apivision.entities.Adoption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AdoptionDAO implements IDAO<Adoption> {

    private static EntityManagerFactory emf;

    public AdoptionDAO() {
        emf = HibernateConfig.getEntityManagerFactory("adoption_db");
    }

    @Override
    public List<AdoptionDTO> getAll() {
        List<Adoption> adoptionList = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Adoption> query = em.createQuery("SELECT a FROM Adoption a", Adoption.class);
            return AdoptionDTO.toDTOList(query.getResultList());
        }
    }

    @Override
    public Adoption getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Adoption.class, id);
        }
    }

    @Override
    public void save(Adoption entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        }
    }

    @Override
    public void update(Adoption entity) {
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
            Adoption adoption = em.find(Adoption.class, id);
            if (adoption != null) {
                em.remove(adoption);
            }
            transaction.commit();
        }
    }
}