package src.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Word {
    //private enum Gender {masculine, feminine, neutral}

    private String word;
    private String mainTranslation;
    private List<String> alternateEnglishTranslations = new ArrayList<>();
    private List<String> alternateSpanishTranslations = new ArrayList<>();
    private String wordTopic;
    private char gender;
    private String spanishPlural;
    private String englishPlural;
    private String stem;
    private String infinitive;
    private HashMap<String, ArrayList<String>> irregularConjugations = new HashMap<String, ArrayList<String>>() ;


    public Word (String word, String mainTranslation, List<String> alternateEnglishTranslations, List<String> alternateSpanishTranslations,
                      String wordTopic, char gender, String spanishPlural, String englishPlural, String stem, String infinitive,
                    HashMap<String, ArrayList<String>> irregularConjugations){
        this.word = word;
        this.mainTranslation = mainTranslation;
        this.alternateEnglishTranslations = alternateEnglishTranslations;
        this.alternateSpanishTranslations = alternateSpanishTranslations;
        this.wordTopic = wordTopic;
        this.gender = gender;
        this.spanishPlural = spanishPlural;
        this.englishPlural = englishPlural;
        this.stem = stem;
        this.infinitive = infinitive;
        this.irregularConjugations = irregularConjugations;
    }

    public String getMainTranslation(){
        return mainTranslation;
    }

    public String getWord(){
        return word;
    }

    public List<String> getAlternateEnglishTranslations() {
        return alternateEnglishTranslations;
    }

    public List<String> getAlternateSpanishTranslations() {
        return alternateSpanishTranslations;
    }

    public String getSpanishPlural() {
        return spanishPlural;
    }

    public String getEnglishPlural() {
        return englishPlural;
    }

    public String getStem() {
        return stem;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public HashMap<String, ArrayList<String>> getIrregularConjugations() {
        return irregularConjugations;
    }

    public String getWordTopic() {
        return wordTopic;
    }

    public char getGender() {
        return gender;
    }
}
