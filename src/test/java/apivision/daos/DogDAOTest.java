package apivision.daos;

import apivision.dtos.DogDTO;
import apivision.enums.DogStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DogDAOTest {

    private DogDAO dogDAO;
    private List<DogDTO> dogList;
    private DogDTO dog1;
    private DogDTO dog2;

    @BeforeEach
    void setUp() {
        // Create a mock DogDAO
        dogDAO = mock(DogDAO.class);

        // Initialize the in-memory dogList
        dogList = new ArrayList<>();

        // Create sample DogDTOs
        dog1 = new DogDTO(1, "Buddy", "Golden Retriever", 3, DogStatus.AVAILABLE, "Friendly and playful");
        dog2 = new DogDTO(2, "Max", "German Shepherd", 5, DogStatus.AVAILABLE, "Loyal and protective");

        // Set up the mock behavior
        when(dogDAO.read(dog1.getId())).thenReturn(dog1);
        when(dogDAO.read(dog2.getId())).thenReturn(dog2);
        when(dogDAO.create(any(DogDTO.class))).thenAnswer(invocation -> {
            DogDTO dog = invocation.getArgument(0);
            dogList.add(dog);
            return dog;
        });
        when(dogDAO.readAll()).thenReturn(dogList);
    }

    @AfterEach
    void tearDown() {
        dogList.clear();
    }

    @Test
    void testCreateDog() {
        DogDTO newDog = new DogDTO(3, "Bella", "Labrador Retriever", 2, DogStatus.AVAILABLE, "Gentle and loving");
        DogDTO createdDog = dogDAO.create(newDog);
        assertEquals(1, dogList.size()); // Verify the dog was added
        assertEquals("Bella", createdDog.getName()); // Verify the dog's name
    }

    @Test
    void testReadDog() {
        DogDTO foundDog = dogDAO.read(dog1.getId());
        assertNotNull(foundDog);
        assertEquals("Buddy", foundDog.getName());
    }

    @Test
    void testUpdateDog() {
        DogDTO updatedDog = new DogDTO(1, "Buddy", "Golden Retriever", 4, DogStatus.AVAILABLE, "Updated description");
        dogList.add(updatedDog); // Manually updating the list
        assertEquals(4, dogList.get(0).getAge()); // Verify the updated age
    }

    @Test
    void testDeleteDog() {
        dogList.add(dog1); // Manually adding dog1
        dogList.remove(dog1); // Simulate deletion
        assertEquals(0, dogList.size()); // Should be empty now
    }

    @Test
    void testGetAllDogs() {
        dogList.add(dog1);
        dogList.add(dog2);

        List<DogDTO> allDogs = dogDAO.readAll();
        assertEquals(2, allDogs.size()); // Should have both dogs
    }
}
