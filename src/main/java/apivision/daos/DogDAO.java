package apivision.daos;

import apivision.dtos.DogDTO;
import apivision.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DogDAO {

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
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null ? DogDTO.convertToDTO(dog) : null;
        }
    }

    // Read all dogs
    public List<DogDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
            List<Dog> dogs = query.getResultList();
            return dogs.stream().map(DogDTO::convertToDTO).toList();
        }
    }

    // Create a new dog
    public DogDTO create(DogDTO dogDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = new Dog(dogDTO);  // Using the constructor that accepts DogDTO
            em.persist(dog);
            em.getTransaction().commit();
            return DogDTO.convertToDTO(dog);  // Convert to DogDTO after persisting
        }
    }

    // Update a dog by ID
    public DogDTO update(Integer id, DogDTO dogDTO) {
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
                return DogDTO.convertToDTO(dog);  // Convert to DogDTO after updating
            }
            return null;  // Return null if dog is not found
        }
    }

    // Delete a dog by ID
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                em.remove(dog);
            }
            em.getTransaction().commit();
        }
    }

    // Validate Primary Key (Check if Dog with this ID exists)
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null;
        }
    }
}
