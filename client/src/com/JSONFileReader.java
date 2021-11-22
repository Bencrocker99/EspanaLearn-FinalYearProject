package src.com;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONFileReader {

    private static HashMap<String, Topic> topicHashMap = new HashMap<String, Topic>();
    private static HashMap<Integer, Level> levelsHashMap = new HashMap<Integer, Level>();
    private static ArrayList<Topic> topicList = new ArrayList<Topic>();

    // Read words in from words.json
    public HashMap<String, Topic> readJsonWordsFile() throws JSONException, IOException {
        String wordsFilePath = "client/src/com/words.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(wordsFilePath)));
        // For each topic within words.json, create a Topic object and add it to a list and hashmap for easy access
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray topicArray = jsonObject.getJSONArray("topics");
        for (int i = 0; i < topicArray.length(); i++){
            String topicName = topicArray.getJSONObject(i).getString("topicName");
            Topic topic = new Topic(topicName);
            topicHashMap.put(topicName, topic);
            topicList.add(topic);
        }
        // For each word, extract all variables from JSON files and add a word object which stores said variables
        JSONArray wordArray = jsonObject.getJSONArray("words");
        for (int i = 0; i < wordArray.length(); i++) {
            List<String> alternateEnglishTranslations = new ArrayList<String>();
            List<String> alternateSpanishTranslations = new ArrayList<String>();
            HashMap<String, ArrayList<String>> irregularConjugations = new HashMap<String, ArrayList<String>>();
            String word = wordArray.getJSONObject(i).getString("word");
            String mainTranslation = wordArray.getJSONObject(i).getString("mainTranslation");
            JSONArray englishTranslationsArray = wordArray.getJSONObject(i).getJSONArray("alternateEnglishTranslations");
            int alternateEnglishSize = wordArray.getJSONObject(i).getJSONArray("alternateEnglishTranslations").length();
            for (int j = 0; j < alternateEnglishSize; j++) {
                String alternateEnglishTranslation = (String) englishTranslationsArray.get(j);
                alternateEnglishTranslations.add(alternateEnglishTranslation);
            }
            JSONArray spanishTranslationsArray = wordArray.getJSONObject(i).getJSONArray("alternateSpanishTranslations");
            int alternateSpanishSize = wordArray.getJSONObject(i).getJSONArray("alternateSpanishTranslations").length();
            for (int j = 0; j < alternateSpanishSize; j++) {
                String alternateSpanishTranslation = (String) spanishTranslationsArray.get(j);
                alternateSpanishTranslations.add(alternateSpanishTranslation);
            }
            String topic = wordArray.getJSONObject(i).getString("topic");
            char gender = (wordArray.getJSONObject(i).getString("gender")).charAt(0);
            String spanishPlural = wordArray.getJSONObject(i).getString("spanishPlural");
            String englishPlural = wordArray.getJSONObject(i).getString("englishPlural");
            String stem = wordArray.getJSONObject(i).getString("stem");
            String infinitive = wordArray.getJSONObject(i).getString("infinitive");
            // Store each irregular conjugation as an array list of strings, starting with a string representing the language and
            // tense, and then 6 elements representing the verb conjugation for each of the 6 persons
            JSONArray irregularConjugationsArray = wordArray.getJSONObject(i).getJSONArray("irregularConjugations");
            int irregularConjugationsSize = irregularConjugationsArray.length();
            for (int j = 0; j < irregularConjugationsSize; j++){
                JSONArray individualIrregularConjugationsArray = (JSONArray) irregularConjugationsArray.get(j);
                ArrayList<String> currentIrregularConjugations = new ArrayList<String>();
                for (int k = 1; k < individualIrregularConjugationsArray.length(); k++){
                    currentIrregularConjugations.add((String) individualIrregularConjugationsArray.get(k));
                }
                irregularConjugations.put((String) individualIrregularConjugationsArray.get(0), currentIrregularConjugations);
            }
            Word newWord = new Word(word, mainTranslation, alternateEnglishTranslations, alternateSpanishTranslations, topic, gender, spanishPlural, englishPlural, stem, infinitive, irregularConjugations);

            (topicHashMap.get(topic)).addWord(newWord);
        }
        return topicHashMap;

    }

    public HashMap<Integer, Level> readJsonLevelsFile() throws JSONException, IOException {
        // read levels in from levels.json
        // For each level, extract all variables from JSON files and add a level object which stores said variables
        String wordsFilePath = "client/src/com/levels.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(wordsFilePath)));

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray levelArray = jsonObject.getJSONArray("levels");
        for (int i = 0; i < levelArray.length(); i++) {
            ArrayList<ArrayList<Topic>> possibleStructures = new ArrayList<ArrayList<Topic>>();
            ArrayList<Integer> orderedProbabilities = new ArrayList<Integer>();
            ArrayList<ArrayList<String>> possibleTenses = new ArrayList<ArrayList<String>>();
            int levelNumber = Integer.parseInt(levelArray.getJSONObject(i).getString("levelNumber"));
            int endingScore = Integer.parseInt(levelArray.getJSONObject(i).getString("endingScore"));
            JSONArray possibleStructuresArray = levelArray.getJSONObject(i).getJSONArray("possibleStructures");
            JSONArray orderedProbabilitiesArray = levelArray.getJSONObject(i).getJSONArray("orderedProbabilities");
            JSONArray possibleTensesArray = levelArray.getJSONObject(i).getJSONArray("possibleTenses");
            int includePluralsChance = Integer.parseInt(levelArray.getJSONObject(i).getString("includePluralsChance"));
            // Store a list of each possible structure (a list of topic objects) for the level
            for (int j = 0; j < possibleStructuresArray.length(); j++) {
                JSONArray individualPossibleStructuresArray = (JSONArray) possibleStructuresArray.get(j);
                ArrayList<Topic> currentPossibleStructures = new ArrayList<Topic>();
                for (int k = 0; k < individualPossibleStructuresArray.length(); k++) {
                    String topicName = (String) individualPossibleStructuresArray.get(k);
                    Topic currentTopic = (topicHashMap.get(topicName));
                    currentPossibleStructures.add(currentTopic);
                }
                possibleStructures.add(currentPossibleStructures);
            }
            for (int j = 0; j < orderedProbabilitiesArray.length(); j++) {
                orderedProbabilities.add((Integer) orderedProbabilitiesArray.get(j));
            }

            for (int j = 0; j < possibleTensesArray.length(); j++ ){
                JSONArray individualPossibleTensesArray = (JSONArray) possibleTensesArray.get(j);
                ArrayList<String> currentPossibleTenses = new ArrayList<String>();
                for (int k = 0; k < individualPossibleTensesArray.length(); k++) {
                    currentPossibleTenses.add((String) individualPossibleTensesArray.get(k));
                }
                possibleTenses.add(currentPossibleTenses);
            }

            Level newLevel = new Level(levelNumber, endingScore, possibleStructures, orderedProbabilities, possibleTenses, includePluralsChance);

            levelsHashMap.put(levelNumber, newLevel);
        }

            return levelsHashMap;
        }

    public static HashMap<String, Topic> getTopicHashMap() {
        return topicHashMap;
    }

    public static HashMap<Integer, Level> getLevelsHashMap(){
        return levelsHashMap;
    }

    public static ArrayList<Topic> getTopicList() {
        return topicList;
    }

}
