package game.service;

import game.model.Word;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class WordService {

    @Autowired
    RestTemplate restTemplate;

    @CircuitBreaker(name = "randomWordCircuitBreaker", fallbackMethod = "fallbackRandomWord")
    public Word getRandomWord(){
        Word selectedWord = restTemplate.getForObject("http://words-service/words/randomword", Word.class);
        return selectedWord;
    }

    public Word fallbackRandomWord(){
        return new Word(-1L, "Salvaguarda");
    }
}
