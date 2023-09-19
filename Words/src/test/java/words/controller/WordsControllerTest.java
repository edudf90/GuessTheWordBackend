package words.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import words.model.Word;
import words.service.WordsService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WordsController.class)
class WordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordsService wordsServiceMock;

    private final Word testWord = new Word(-2L, "Test");

    @Test
    public void getRandomWordShouldReturnAWordFromService(){
        when(wordsServiceMock.getRandomWord()).thenReturn(testWord);
        Gson gson = new Gson();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/words/randomword");
        try {
            mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().json(gson.toJson(testWord)));
            verify(wordsServiceMock).getRandomWord();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//.andExpect(status().isBadRequest());
}