package apivision.daos;

import apivision.dtos.DogDTO;
import apivision.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DogDAO {

    private static final Logger logger = LoggerFactory.getLogger(DogDAO.class);
    private static DogDAO instance;
    private static EntityManagerFactory emf;

    // Singleton pattern for DAO instance
    public static DogDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogDAO();
        }
        return instance;
    }

    // Read a dog by ID
    public DogDTO read(Integer id) {
        logger.info("Attempting to read dog with ID: {}", id);
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null ? DogDTO.convertToDTO(dog) : null;
        } catch (Exception e) {
            logger.error("Error reading dog with ID: {}", id, e);
            return null;
        }
    }

    // Read all dogs
    public List<DogDTO> readAll() {
        logger.info("Fetching all dogs");
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
            List<Dog> dogs = query.getResultList();
            return dogs.stream().map(DogDTO::convertToDTO).toList();
        } catch (Exception e) {
            logger.error("Error fetching all dogs", e);
            return List.of(); // Return empty list on error
        }
    }

    // Create a new dog
    public DogDTO create(DogDTO dogDTO) {
        logger.info("Creating a new dog: {}", dogDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = new Dog(dogDTO);
            em.persist(dog);
            em.getTransaction().commit();
            return DogDTO.convertToDTO(dog);
        } catch (Exception e) {
            logger.error("Error creating dog", e);
            return null; // Or consider throwing a custom exception
        }
    }

    // Update a dog by ID
    public DogDTO update(Integer id, DogDTO dogDTO) {
        logger.info("Updating dog with ID: {}", id);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                dog.setName(dogDTO.getName());
                dog.setBreed(dogDTO.getBreed());
                dog.setAge(dogDTO.getAge());
                dog.setStatus(dogDTO.getStatus());
                dog.setDescription(dogDTO.getDescription());
                em.merge(dog);
                em.getTransaction().commit();
                return DogDTO.convertToDTO(dog);
            }
            return null; // Return null if dog not found
        } catch (Exception e) {
            logger.error("Error updating dog with ID: {}", id, e);
            return null;
        }
    }

    // Delete a dog by ID
    public void delete(Integer id) {
        logger.info("Deleting dog with ID: {}", id);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                em.remove(dog);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error deleting dog with ID: {}", id, e);
        }
    }

    // Validate Primary Key (Check if Dog with this ID exists)
    public boolean validatePrimaryKey(Integer id) {
        logger.info("Validating primary key for ID: {}", id);
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null;
        } catch (Exception e) {
            logger.error("Error validating primary key for ID: {}", id, e);
            return false;
        }
    }
}
