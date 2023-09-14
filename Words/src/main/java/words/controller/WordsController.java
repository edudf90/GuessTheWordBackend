package words.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import words.model.Word;
import words.model.WordRepository;
import words.service.WordsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("words")
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @GetMapping("/randomword")
    public Word getRandomWord(){
        return wordsService.getRandomWord();
    }
}
