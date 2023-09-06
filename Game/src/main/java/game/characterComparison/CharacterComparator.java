package game.characterComparison;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CharacterComparator {

    private Map<Character, Character[]> specialCasesDictionary;

    public CharacterComparator() {
        this.specialCasesDictionary = new HashMap<>();
        this.specialCasesDictionary.put('a', new Character[]{'ã', 'â', 'á', 'à'});
        this.specialCasesDictionary.put('e', new Character[]{'ê', 'é'});
        this.specialCasesDictionary.put('i', new Character[]{'í'});
        this.specialCasesDictionary.put('o', new Character[]{'õ', 'ô', 'ó'});
        this.specialCasesDictionary.put('u', new Character[]{'ú'});
        this.specialCasesDictionary.put('c', new Character[]{'ç'});
    }

    public boolean specialCaseCharacterComparison(Character firstCharacter, Character secondCharacter){
        Character[] firstCharacterArray = this.specialCasesDictionary.get(firstCharacter);
        if (firstCharacterArray != null && Arrays.stream(firstCharacterArray).toList().contains(secondCharacter)){
            return true;
        }
        Character[] secondCharacterArray = this.specialCasesDictionary.get(secondCharacter);
        if (secondCharacterArray != null && Arrays.stream(secondCharacterArray).toList().contains(firstCharacter)){
            return true;
        }
        return false;
    }
}
