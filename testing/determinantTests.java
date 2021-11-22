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

import static org.junit.Assert.assertEquals;

public class determinantTests {

    TranslationHandler translationHandler = new TranslationHandler();
    JSONFileReader jsonFileReader = new JSONFileReader();
    HashMap<String, Topic> topicHashMap;
    List<Word> basicNouns;
    Word introductoryDeterminant;
    Word continuationDeterminant;

    @Before
    public void readWords() throws IOException, JSONException {
        jsonFileReader.readJsonWordsFile();
        topicHashMap = jsonFileReader.getTopicHashMap();
        basicNouns = topicHashMap.get("basicNouns").getTopicWords();
        introductoryDeterminant = topicHashMap.get("introductoryDeterminants").getTopicWords().get(0);
        continuationDeterminant = topicHashMap.get("continuationDeterminants").getTopicWords().get(0);
    }

    @Test
    public void introductoryDeterminantCorrecterTest(){
        ArrayList<Word> phrase = new ArrayList<Word>();
        phrase.add(introductoryDeterminant);
        phrase.add(basicNouns.get(0));

        ArrayList<String> exactPhrase = new ArrayList<String>();
        exactPhrase.add("a");
        exactPhrase.add("boy");

        String correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("a", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("un", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("some");
        exactPhrase.add("boys");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("some", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("unos", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(introductoryDeterminant);
        phrase.add(basicNouns.get(1));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("a");
        exactPhrase.add("girl");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("a", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("una", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("some");
        exactPhrase.add("girls");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("some", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("unas", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(introductoryDeterminant);
        phrase.add(basicNouns.get(0));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("un");
        exactPhrase.add("chico");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("un", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("a", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("unos");
        exactPhrase.add("chicos");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("unos", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("some", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(introductoryDeterminant);
        phrase.add(basicNouns.get(1));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("una");
        exactPhrase.add("chica");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("una", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("a", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("unas");
        exactPhrase.add("chicas");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("unas", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("some", correctDeterminant);
    }

    @Test
    public void continuationDeterminantCorrecterTest(){
        ArrayList<Word> phrase = new ArrayList<Word>();
        phrase.add(continuationDeterminant);
        phrase.add(basicNouns.get(0));

        ArrayList<String> exactPhrase = new ArrayList<String>();
        exactPhrase.add("the");
        exactPhrase.add("boy");

        String correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("el", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("the");
        exactPhrase.add("boys");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("los", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(continuationDeterminant);
        phrase.add(basicNouns.get(1));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("la");
        exactPhrase.add("girl");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("la", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("the");
        exactPhrase.add("girls");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("las", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(continuationDeterminant);
        phrase.add(basicNouns.get(0));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("el");
        exactPhrase.add("chico");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("el", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("los");
        exactPhrase.add("chicos");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("los", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        phrase = new ArrayList<Word>();
        phrase.add(continuationDeterminant);
        phrase.add(basicNouns.get(1));

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("la");
        exactPhrase.add("chica");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("la", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

        exactPhrase = new ArrayList<String>();
        exactPhrase.add("las");
        exactPhrase.add("chicas");

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, false);
        assertEquals("las", correctDeterminant);

        correctDeterminant = translationHandler.determinantCorrecter(0, phrase, exactPhrase, true);
        assertEquals("the", correctDeterminant);

    }


}
