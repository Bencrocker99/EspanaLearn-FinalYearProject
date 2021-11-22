import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import src.com.JSONFileReader;
import src.com.Topic;
import src.com.TranslationHandler;
import src.com.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class translationTests {

    TranslationHandler translationHandler = new TranslationHandler();
    JSONFileReader jsonFileReader = new JSONFileReader();
    HashMap<String, Topic> topicHashMap;
    List<Word> basicInterjections;
    List<Word> basicNouns;
    List<Word> basicAdjectives;
    List<Word> travellingVerbs;
    Word introductoryDeterminant;
    Word continuationDeterminant;
    Word permanentBeingVerb;

    @Before
    public void readWords() throws IOException, JSONException {
        jsonFileReader.readJsonWordsFile();
        topicHashMap = jsonFileReader.getTopicHashMap();
        basicInterjections = topicHashMap.get("basicInterjections").getTopicWords();
        basicNouns = topicHashMap.get("basicNouns").getTopicWords();
        System.out.println(basicNouns.get(0).getWord());
        basicAdjectives = topicHashMap.get("basicAdjectives").getTopicWords();
        System.out.println(basicAdjectives.get(0).getWord());
        travellingVerbs = topicHashMap.get("travellingVerbs").getTopicWords();
        permanentBeingVerb = topicHashMap.get("permanentBeingVerbs").getTopicWords().get(0);
        introductoryDeterminant = topicHashMap.get("introductoryDeterminants").getTopicWords().get(0);
        continuationDeterminant = topicHashMap.get("continuationDeterminants").getTopicWords().get(0);
    }

    @Test
    public void basicInterjectionsTest(){
        ArrayList<Integer> verbPersons = new ArrayList<Integer>();
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<String> exactWords = new ArrayList<String>();

        words.add(basicInterjections.get(0));
        exactWords.add("hello");
        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        String correctTranslation = "hola";

        ArrayList<String> translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        exactWords = new ArrayList<String>();
        exactWords.add("hola");
        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "hello";

       translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());
    }

    @Test
    public void basicInterjectionsSynonymTest(){
        ArrayList<Integer> verbPersons = new ArrayList<Integer>();
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<String> exactWords = new ArrayList<String>();

        words.add(basicInterjections.get(0));
        exactWords.add("hello");
        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        String correctTranslation = "caramba";

        ArrayList<String> translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        exactWords = new ArrayList<String>();
        exactWords.add("hola");
        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "hi";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());
    }

    @Test
    public void basicSentenceTest(){
        ArrayList<Integer> verbPersons = new ArrayList<Integer>();
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<String> exactWords = new ArrayList<String>();

        words.add(introductoryDeterminant);
        words.add(basicNouns.get(0));
        words.add(basicAdjectives.get(0));

        exactWords.add("a");
        exactWords.add("boy");
        exactWords.add("ugly");

        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        String correctTranslation = "un chico feo";

        ArrayList<String> translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(introductoryDeterminant);
        words.add(basicNouns.get(0));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("un");
        exactWords.add("chico");
        exactWords.add("feo");

        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "an ugly boy";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(continuationDeterminant);
        words.add(basicNouns.get(1));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("the");
        exactWords.add("girl");
        exactWords.add("ugly");

        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        correctTranslation = "la chica fea";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(continuationDeterminant);
        words.add(basicNouns.get(1));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("la");
        exactWords.add("chica");
        exactWords.add("fea");

        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "the ugly girl";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());
    }

    @Test
    public void basicSentenceSynonymsTest(){
        ArrayList<Integer> verbPersons = new ArrayList<Integer>();
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<String> exactWords = new ArrayList<String>();

        words.add(introductoryDeterminant);
        words.add(basicNouns.get(0));
        words.add(basicAdjectives.get(0));

        exactWords.add("a");
        exactWords.add("boy");
        exactWords.add("ugly");

        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        String correctTranslation = "un niño asqueroso";

        ArrayList<String> translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(introductoryDeterminant);
        words.add(basicNouns.get(0));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("un");
        exactWords.add("chico");
        exactWords.add("feo");

        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "an unsightly child";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(continuationDeterminant);
        words.add(basicNouns.get(1));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("the");
        exactWords.add("girl");
        exactWords.add("ugly");

        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        correctTranslation = "la niña asquerosa";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        words = new ArrayList<Word>();
        words.add(continuationDeterminant);
        words.add(basicNouns.get(1));
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("la");
        exactWords.add("chica");
        exactWords.add("fea");

        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "the unsightly child";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());
    }

    @Test
    public void beingSentenceTest() {
        ArrayList<Integer> verbPersons = new ArrayList<Integer>();
        verbPersons.add(1);
        ArrayList<Word> words = new ArrayList<Word>();
        ArrayList<String> exactWords = new ArrayList<String>();

        words.add(permanentBeingVerb);
        words.add(basicAdjectives.get(0));

        exactWords.add("I am");
        exactWords.add("ugly");

        exactWords = helperFunction(words, exactWords, true, "present", verbPersons);
        String correctTranslation = "soy feo";

        ArrayList<String> translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, true);
        assertTrue(translationReturn.isEmpty());

        verbPersons = new ArrayList<Integer>();
        verbPersons.add(1);
        words = new ArrayList<Word>();
        words.add(permanentBeingVerb);
        words.add(basicAdjectives.get(0));
        exactWords = new ArrayList<String>();
        exactWords.add("soy");
        exactWords.add("feo");

        exactWords = helperFunction(words, exactWords, false, "present", verbPersons);
        correctTranslation = "I am ugly";

        translationReturn = translationHandler.verifyTranslation(words, exactWords, correctTranslation, false);
        assertTrue(translationReturn.isEmpty());
    }

    @Test
    public void beingSentenceSynonymTest() {

    }

    public ArrayList<String> helperFunction(ArrayList<Word> currentWords, ArrayList<String> currentExactWords, boolean englishToSpanish, String tense, ArrayList<Integer> verbPersons ){
        for (int i = 0; i < currentWords.size(); i++) {
            if (currentWords.get(i).getWordTopic().contains("Determinants")) { // correct determinants
                String correctDeterminant = translationHandler.determinantCorrecter(i, currentWords, currentExactWords, englishToSpanish);
                currentExactWords.set(i, correctDeterminant);
            }
            if (currentWords.get(i).getWordTopic().contains("Verbs")) { // conjugate verbs, verb should refer to nearest noun, if there isn't one than be random
                int person = verbPersons.get(0);
                verbPersons.remove(0);
                boolean needNoun = true;
                for (int j = i; j >= 0; j--) {
                    if (currentWords.get(j).getWordTopic().contains("Nouns")) {
                        needNoun = false;
                    }
                }
                // of there is a noun before verb dont need the I you etc before verb in english
                String correctVerb = translationHandler.verbConjugator(currentWords.get(i), englishToSpanish, tense, person, needNoun, true);
                currentExactWords.set(i, correctVerb);
            }
            if (currentWords.get(i).getWordTopic().contains("Adjectives")) { // Adjectives need to agree with noun/certain verbs
                String correctAdjective = translationHandler.adjectiveAgree(i, currentWords, currentExactWords, true, englishToSpanish);
                currentExactWords.set(i, correctAdjective);
            }
        }

        if (englishToSpanish) { // Word order is structures follows Spanish principles, so need to correct if initial phrase is English instead
            currentExactWords = translationHandler.wordOrderCorrecter(currentWords, currentExactWords);
            currentWords = translationHandler.wordOrderCorrecter(currentWords);
        }

        if (!englishToSpanish) {
            ArrayList<Integer> wordsToRemove = new ArrayList<Integer>(); // Store positions of elements which need to be removed
            for (int i = 0; i < currentExactWords.size() - 1; i++) {
                if (currentExactWords.get(i).equals("a") && currentExactWords.get(i + 1).equals("el")) {
                    currentExactWords.set(i, currentWords.get(i).getAlternateSpanishTranslations().get(0)); // a el becomes al
                    wordsToRemove.add(i + 1);
                }
            }
            for (int i = wordsToRemove.size() - 1; i >= 0; i--) { // Remove elements in reverse order to prevent concurrent exceptions
                int position = wordsToRemove.get(i);
                currentWords.remove(position);
                currentExactWords.remove(position);
            }
        }
        System.out.println(currentExactWords);
        return currentExactWords;
    }


}
