package words.model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String word;

    public Word() {
    }

    public Word(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
