package words.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class WordRepositoryTest {

    @Container
    @ServiceConnection
    static private PostgreSQLContainer container = new PostgreSQLContainer("postgres:alpine");

    @Autowired
    private WordRepository repository;

    @Test
    public void findByIdMustReturnCorrectWord(){
        Word testWord = new Word(1L, "Avocado");

        Optional<Word> result = repository.findById(testWord.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get().getWord(), testWord.getWord());
        assertEquals(result.get().getId(), testWord.getId());
    }

}