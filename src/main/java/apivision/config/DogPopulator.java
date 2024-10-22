package apivision.config;

import apivision.entities.Dog;
import apivision.enums.DogStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DogPopulator {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        EntityManager em = null;

        try {
            em = emf.createEntityManager(); // Initialize EntityManager
            em.getTransaction().begin();


            Dog dog1 = new Dog(0, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
            Dog dog2 = new Dog(0, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");
            Dog dog3 = new Dog(0, "Bella", "Labrador Retriever", 2, DogStatus.ADOPTED, "Gentle and loving");
            Dog dog4 = new Dog(0, "Charlie", "Beagle", 4, DogStatus.AVAILABLE, "Curious and merry");
            Dog dog5 = new Dog(0, "Lucy", "Bulldog", 6, DogStatus.AVAILABLE, "Calm and courageous");

            // Persist
            em.persist(dog1);
            em.persist(dog2);
            em.persist(dog3);
            em.persist(dog4);
            em.persist(dog5);

            em.getTransaction().commit();
            System.out.println("Dogs populated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
