package words.model;

import org.springframework.data.jpa.repository.JpaRepository;
import words.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
}
