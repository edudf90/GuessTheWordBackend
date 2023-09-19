package words.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import words.model.Word;
import words.model.WordRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class WordsServiceTest {

    @MockBean
    private WordRepository wordRepositoryMock;

    private Random random;
    private WordsService wordsService;

    private Word testValidWord;
    private Word fallbackWord;

    @BeforeEach
    void setUp() {
        wordRepositoryMock = mock(WordRepository.class);
        testValidWord = new Word(6L, "Avocado");
        fallbackWord = WordsService.FALLBACK_WORD;
        random = new Random(1L);
        wordsService = new WordsService(wordRepositoryMock, random);
    }

    @Test
    void getRandomWordShouldReturnTheWordFromRepositoryWhenItHitsAValidId() {
        when(wordRepositoryMock.findById(6L)).thenReturn(Optional.of(testValidWord));
        when(wordRepositoryMock.count()).thenReturn(10L);

        Word result = wordsService.getRandomWord();

        assertEquals(testValidWord.getId(), result.getId());
        assertEquals(testValidWord.getWord(), result.getWord());
        verify(wordRepositoryMock).count();
        verify(wordRepositoryMock).findById(6L);
    }


    @Test
    void getRandomWordShouldReturnTheFallbackWordWhenItMissesAValidId() {

        when(wordRepositoryMock.findById(6L)).thenReturn(Optional.empty());
        when(wordRepositoryMock.count()).thenReturn(10L);

        Word result = wordsService.getRandomWord();

        assertEquals(fallbackWord.getId(), result.getId());
        assertEquals(fallbackWord.getWord(), result.getWord());
        verify(wordRepositoryMock).count();
        verify(wordRepositoryMock).findById(6L);
    }


}