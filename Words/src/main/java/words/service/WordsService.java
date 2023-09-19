package words.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import words.model.Word;
import words.model.WordRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class WordsService {

    public static final Word FALLBACK_WORD = new Word(0L, "Fallback");
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private Random random;

    public WordsService(WordRepository wordRepository, Random random) {
        this.wordRepository = wordRepository;
        this.random = random;
    }

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
