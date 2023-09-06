package words.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import words.model.Word;
import words.model.WordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("words")
public class WordsController {

    public static final Word FALLBACK_WORD = new Word(0L, "Reserva");
    @Autowired
    private WordRepository wordRepository;
    private Random random = new Random();

    @GetMapping("/randomword")
    public Word getRandomWord(){
        Long maxId = wordRepository.count();
        Long index = random.nextLong(maxId);
        Optional<Word> selectedWord = wordRepository.findById(index);
        if (!selectedWord.isPresent()){
            return FALLBACK_WORD;
        }
        return selectedWord.get();
    }
}
