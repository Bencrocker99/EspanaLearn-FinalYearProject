import org.json.JSONException;
import org.junit.Assert;
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

import static org.junit.Assert.assertEquals;

public class adjectiveTests {

    TranslationHandler translationHandler = new TranslationHandler();
    JSONFileReader jsonFileReader = new JSONFileReader();
    HashMap<String, Topic> topicHashMap;
    List<Word> basicAdjectives;
    List<Word> basicNouns;


    @Before
    public void readWords() throws IOException, JSONException {
        jsonFileReader.readJsonWordsFile();
        topicHashMap = jsonFileReader.getTopicHashMap();
        basicAdjectives = topicHashMap.get("basicAdjectives").getTopicWords();
        basicNouns = topicHashMap.get("basicNouns").getTopicWords();
    }


    @Test
    public void spanishAdjectiveEndingsTest() {
        String masculineSingular = translationHandler.adjectiveConjugator(false, 'm', basicAdjectives.get(0), false);
        String feminineSingular = translationHandler.adjectiveConjugator(false, 'f', basicAdjectives.get(0), false);
        String masculinePlural = translationHandler.adjectiveConjugator(true, 'm', basicAdjectives.get(0), false);
        String femininePlural = translationHandler.adjectiveConjugator(true, 'f', basicAdjectives.get(0), false);

        assertEquals("feo", masculineSingular);
        assertEquals("fea", feminineSingular);
        assertEquals("feos", masculinePlural);
        assertEquals("feas", femininePlural);

        masculineSingular = translationHandler.adjectiveConjugator(false, 'm', basicAdjectives.get(2), false);
        feminineSingular = translationHandler.adjectiveConjugator(false, 'f', basicAdjectives.get(2), false);
        masculinePlural = translationHandler.adjectiveConjugator(true, 'm', basicAdjectives.get(2), false);
        femininePlural = translationHandler.adjectiveConjugator(true, 'f', basicAdjectives.get(2), false);

        assertEquals("grande", masculineSingular);
        assertEquals("grande", feminineSingular);
        assertEquals("grandes", masculinePlural);
        assertEquals("grandes", femininePlural);

        masculineSingular = translationHandler.adjectiveConjugator(false, 'm', basicAdjectives.get(5), false);
        feminineSingular = translationHandler.adjectiveConjugator(false, 'f', basicAdjectives.get(5), false);
        masculinePlural = translationHandler.adjectiveConjugator(true, 'm', basicAdjectives.get(5), false);
        femininePlural = translationHandler.adjectiveConjugator(true, 'f', basicAdjectives.get(5), false);

        assertEquals("joven", masculineSingular);
        assertEquals("joven", feminineSingular);
        assertEquals("jóvenes", masculinePlural);
        assertEquals("jóvenes", femininePlural);
    }

    @Test
    public void englishAdjectiveEndingsTest(){
        String masculineSingular = translationHandler.adjectiveConjugator(false, 'm', basicAdjectives.get(0), true);
        String feminineSingular = translationHandler.adjectiveConjugator(false, 'f', basicAdjectives.get(0), true);
        String masculinePlural = translationHandler.adjectiveConjugator(true, 'm', basicAdjectives.get(0), true);
        String femininePlural = translationHandler.adjectiveConjugator(true, 'f', basicAdjectives.get(0), true);

        assertEquals("ugly", masculineSingular);
        assertEquals("ugly", feminineSingular);
        assertEquals("ugly", masculinePlural);
        assertEquals("ugly", femininePlural);
    }


    @Test
    public void adjectiveAgreeTest(){
        ArrayList<Word> phrase = new ArrayList<Word>();
        phrase.add(basicNouns.get(0));
        phrase.add(basicAdjectives.get(0));

        ArrayList<String> exactPhrase = new ArrayList<String>();
        exactPhrase.add("boy");
        exactPhrase.add("ugly");

        String correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, true);
        assertEquals("ugly", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, false);
        assertEquals("feo", correctAdjective);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("boys");
        exactPhrase.add("ugly");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, true);
        assertEquals("ugly", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, false);
        assertEquals("feos", correctAdjective);

        phrase = new ArrayList<Word>();
        phrase.add(basicNouns.get(1));
        phrase.add(basicAdjectives.get(0));


        exactPhrase = new ArrayList<String>();
        exactPhrase.add("girl");
        exactPhrase.add("ugly");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, true);
        assertEquals("ugly", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, false);
        assertEquals("fea", correctAdjective);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("girls");
        exactPhrase.add("ugly");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, true);
        assertEquals("ugly", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, false);
        assertEquals("feas", correctAdjective);

        phrase = new ArrayList<Word>();
        phrase.add(basicNouns.get(0));
        phrase.add(basicAdjectives.get(0));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("chico");
        exactPhrase.add("feo");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, false);
        assertEquals("feo", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, true);
        assertEquals("ugly", correctAdjective);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("chicos");
        exactPhrase.add("feos");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, false);
        assertEquals("feos", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, true);
        assertEquals("ugly", correctAdjective);

        phrase = new ArrayList<Word>();
        phrase.add(basicNouns.get(1));
        phrase.add(basicAdjectives.get(0));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("chica");
        exactPhrase.add("fea");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, false);
        assertEquals("fea", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, true);
        assertEquals("ugly", correctAdjective);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("chicas");
        exactPhrase.add("feas");

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, true, false);
        assertEquals("feas", correctAdjective);

        correctAdjective = translationHandler.adjectiveAgree(1, phrase, exactPhrase, false, true);
        assertEquals("ugly", correctAdjective);
    }
}

