import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import src.com.JSONFileReader;
import src.com.Topic;
import src.com.TranslationHandler;
import src.com.Word;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class verbTests {

    TranslationHandler translationHandler = new TranslationHandler();
    JSONFileReader jsonFileReader = new JSONFileReader();
    HashMap<String, Topic> topicHashMap;
    List<Word> basicNouns;
    List<Word> basicAdjectives;
    List<Word> travellingVerbs;
    Word regularTravellingVerb;
    Word permanentBeingVerb;


    @Before
    public void readWords() throws IOException, JSONException {
        jsonFileReader.readJsonWordsFile();
        topicHashMap = jsonFileReader.getTopicHashMap();
        basicNouns = topicHashMap.get("basicNouns").getTopicWords();
        basicAdjectives = topicHashMap.get("basicAdjectives").getTopicWords();
        travellingVerbs = topicHashMap.get("travellingVerbs").getTopicWords();
        regularTravellingVerb = travellingVerbs.get(3);
        permanentBeingVerb = topicHashMap.get("permanentBeingVerbs").getTopicWords().get(0);
    }

    @Test
    public void englishPresentRegularEndingsTest() {

       translationHandler.verbConjugator(regularTravellingVerb, false, "present", 1, false, true);
       String person1Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 1, false, false);
       assertEquals("walk", person1Verb);

       translationHandler.verbConjugator(regularTravellingVerb, false, "present", 1, true, true);
       String person1VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 1, true, false);
       assertEquals("I walk", person1VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 2, false, false);
        assertEquals("walk", person2Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 2, true, false);
        assertEquals("you walk", person2VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 3, false, false);
        assertEquals("walks", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 3, true, false);
        assertTrue((person3VerbNoun.equals("he walks")) || (person3VerbNoun.equals("she walks")));

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 4, false, false);
        assertEquals("walk", person4Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 4, true, false);
        assertEquals("we walk", person4VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 5, false, false);
        assertEquals("walk", person5Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 5, true, false);
        assertEquals("you walk", person5VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 6, false, false);
        assertEquals("walk", person6Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "present", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "present", 6, true, false);
        assertEquals("they walk", person6VerbNoun);
    }

    @Test
    public void spanishPresentRegularEndingsTest() {

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 1, false, false);
        assertEquals("ando", person1Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 1, true, false);
        assertEquals("ando", person1VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 2, false, false);
        assertEquals("andas", person2Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 2, true, false);
        assertEquals("andas", person2VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 3, false, false);
        assertEquals("anda", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 3, true, false);
        assertEquals("anda", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 4, false, false);
        assertEquals("andamos", person4Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 4, true, false);
        assertEquals("andamos", person4VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 5, false, false);
        assertEquals("andáis", person5Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 5, true, false);
        assertEquals("andáis", person5VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 6, false, false);
        assertEquals("andan", person6Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "present", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "present", 6, true, false);
        assertEquals("andan", person6VerbNoun);
    }

    @Test
    public void englishPresentIrregularEndingsTest() {

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 1, false, false);
        assertEquals("am", person1Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 1, true, false);
        assertEquals("I am", person1VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 2, false, false);
        assertEquals("are", person2Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 2, true, false);
        assertEquals("you are", person2VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 3, false, false);
        assertEquals("is", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 3, true, false);
        assertTrue((person3VerbNoun.equals("he is")) || (person3VerbNoun.equals("she is")));

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 4, false, false);
        assertEquals("are", person4Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 4, true, false);
        assertEquals("we are", person4VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 5, false, false);
        assertEquals("are", person5Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 5, true, false);
        assertEquals("you are", person5VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 6, false, false);
        assertEquals("are", person6Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "present", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "present", 6, true, false);
        assertEquals("they are", person6VerbNoun);
    }

    @Test
    public void spanishPresentIrregularEndingsTest() {

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 1, false, false);
        assertEquals("soy", person1Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 1, true, false);
        assertEquals("soy", person1VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 2, false, false);
        assertEquals("eres", person2Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 2, true, false);
        assertEquals("eres", person2VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 3, false, false);
        assertEquals("es", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 3, true, false);
        assertEquals("es", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 4, false, false);
        assertEquals("somos", person4Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 4, true, false);
        assertEquals("somos", person4VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 5, false, false);
        assertEquals("sois", person5Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 5, true, false);
        assertEquals("sois", person5VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 6, false, false);
        assertEquals("son", person6Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "present", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "present", 6, true, false);
        assertEquals("son", person6VerbNoun);
    }

    @Test
    public void englishPreteriteRegularEndingsTest() {

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 1, false, false);
        assertEquals("walked", person1Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 1, true, false);
        assertEquals("I walked", person1VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 2, false, false);
        assertEquals("walked", person2Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 2, true, false);
        assertEquals("you walked", person2VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 3, false, false);
        assertEquals("walked", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 3, true, false);
        assertTrue((person3VerbNoun.equals("he walked")) || (person3VerbNoun.equals("she walked")));

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 4, false, false);
        assertEquals("walked", person4Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 4, true, false);
        assertEquals("we walked", person4VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 5, false, false);
        assertEquals("walked", person5Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 5, true, false);
        assertEquals("you walked", person5VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 6, false, false);
        assertEquals("walked", person6Verb);

        translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 6, true, false);
        assertEquals("they walked", person6VerbNoun);
    }

    @Test
    public void spanishPreteriteRegularEndingsTest() {

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 1, false, false);
        assertEquals("anduve", person1Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 1, true, false);
        assertEquals("anduve", person1VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 2, false, false);
        assertEquals("anduviste", person2Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 2, true, false);
        assertEquals("anduviste", person2VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 3, false, false);
        assertEquals("anduvo", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 3, true, false);
        assertEquals("anduvo", person3Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 4, false, false);
        assertEquals("anduvimos", person4Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 4, true, false);
        assertEquals("anduvimos", person4VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 5, false, false);
        assertEquals("anduvisteis", person5Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 5, true, false);
        assertEquals("anduvisteis", person5VerbNoun);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 6, false, false);
        assertEquals("anduvieron", person6Verb);

        translationHandler.verbConjugator(regularTravellingVerb, true, "preterite", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(regularTravellingVerb, false, "preterite", 6, true, false);
        assertEquals("anduvieron", person6VerbNoun);
    }

    @Test
    public void englishPreteriteIrregularEndingsTest() {

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 1, false, false);
        assertEquals("was", person1Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 1, true, false);
        assertEquals("I was", person1VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 2, false, false);
        assertEquals("were", person2Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 2, true, false);
        assertEquals("you were", person2VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 3, false, false);
        assertEquals("was", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 3, true, false);
        assertTrue((person3VerbNoun.equals("he was")) || (person3VerbNoun.equals("she was")));

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 4, false, false);
        assertEquals("were", person4Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 4, true, false);
        assertEquals("we were", person4VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 5, false, false);
        assertEquals("were", person5Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 5, true, false);
        assertEquals("you were", person5VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 6, false, false);
        assertEquals("were", person6Verb);

        translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 6, true, false);
        assertEquals("they were", person6VerbNoun);
    }

    @Test
    public void spanishPreteriteIrregularEndingsTest() {

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 1, false, true);
        String person1Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 1, false, false);
        assertEquals("fui", person1Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 1, true, true);
        String person1VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 1, true, false);
        assertEquals("fui", person1VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 2, false, true);
        String person2Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 2, false, false);
        assertEquals("fuiste", person2Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 2, true, true);
        String person2VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 2, true, false);
        assertEquals("fuiste", person2VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 3, false, true);
        String person3Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 3, false, false);
        assertEquals("fue", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 3, true, true);
        String person3VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 3, true, false);
        assertEquals("fue", person3Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 4, false, true);
        String person4Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 4, false, false);
        assertEquals("fuimos", person4Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 4, true, true);
        String person4VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 4, true, false);
        assertEquals("fuimos", person4VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 5, false, true);
        String person5Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 5, false, false);
        assertEquals("fuisteis", person5Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 5, true, true);
        String person5VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 5, true, false);
        assertEquals("fuisteis", person5VerbNoun);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 6, false, true);
        String person6Verb = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 6, false, false);
        assertEquals("fueron", person6Verb);

        translationHandler.verbConjugator(permanentBeingVerb, true, "preterite", 6, true, true);
        String person6VerbNoun = translationHandler.verbConjugator(permanentBeingVerb, false, "preterite", 6, true, false);
        assertEquals("fueron", person6VerbNoun);
    }

}
