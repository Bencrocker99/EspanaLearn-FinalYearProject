package src.com;

import java.util.ArrayList;
import java.util.Random;

public class TranslationHandler {

    private ArrayList<String> possibleCorrectAnswer = new ArrayList<String>();
    private Random randomGenerator = new Random();
    // Store list of tenses of phrase left to right
    private ArrayList<String> verbTenses = new ArrayList<String>();
    // Store list of persons of phrase left to right
    private ArrayList<Integer> verbPersons = new ArrayList<Integer>();
    // Store list of booleans of whether each adjective in phrase is plural, left to right
    private ArrayList<Boolean> adjectivePlurals = new ArrayList<Boolean>();
    // Store list of genders of phrase left to right
    private ArrayList<Character> adjectiveGenders = new ArrayList<Character>();
    // Store list of booleans of whether each adjective in phrase has a set gender or can be either gender, left to right
    private ArrayList<Boolean> adjectiveGenderIrrelevant = new ArrayList<Boolean>();

    // Called when a handler creates a question for user to answer, and when user submit and answer for the question
    public ArrayList<String> verifyTranslation(ArrayList<Word> translationWords, ArrayList<String> translationExactWords, String submittedTranslation, boolean englishToSpanish) {
        possibleCorrectAnswer.clear();//
        // Sanitise input by ignoring case, punctuation/numbers and trailing spaces
        String lowerCaseSubmittedTranslation = submittedTranslation.toLowerCase();
        String fullySanitisedSubmittedTranslation = lowerCaseSubmittedTranslation.replaceAll("[^a-zA-Záéíóúüñ\\w\\d\\s]", "");
        ArrayList<String> submittedWords = phraseSplitter(fullySanitisedSubmittedTranslation);
        ArrayList<String> translationExactWordsCopy = translationExactWords;

        // Each word, see if the correct form of the translated word finds a match with a user's word
        if (englishToSpanish) {
            for (int i = 0; i < translationWords.size(); i++) { // Going through each translation word/phrase one at a time
                String currentWord = translationWords.get(i).getWord();
                int numberOfSpaces = countSpaces(currentWord);
                if (translationWords.get(i).getWordTopic().contains("Nouns")) { // in the case it is a noun
                    ArrayList<String> correctlyTranslatedNoun = new ArrayList<String>();
                    // Correct noun (e.g. make singular or plural)
                    correctlyTranslatedNoun.add(nounCorrecter(true, translationWords.get(i), translationExactWords.get(i))); // Return correct Spanish Noun
                    possibleCorrectAnswer.add(correctlyTranslatedNoun.get(0));
                    // See if corrected noun appears anywhere in user's submission, and remove if it does
                    int[] determinantResultArray = containsPhrase(correctlyTranslatedNoun, submittedWords);
                    if (determinantResultArray[0] == 1) {
                        submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // if didn't match main translation, check list of alternates and see if any of those match
                    for (int k = 0; k < translationWords.get(i).getAlternateSpanishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateSpanishTranslations().get(k);
                        currentWord =  nounCorrecterSynonym(true, translationWords.get(i), translationExactWords.get(i), currentWord);
                        numberOfSpaces = countSpaces(currentWord);
                        ArrayList<String> adjectiveSynonym = new ArrayList<String>();
                        adjectiveSynonym.add(currentWord);
                        int[] alternateResultArray = (containsPhrase(adjectiveSynonym, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // If found the word which appears in user's words, don't continue
                        }
                    }
                    continue;
                }
                if (translationWords.get(i).getWordTopic().contains("Determinants")) { // in the case it is a determinant
                    ArrayList<String> correctlyTranslatedDeterminant = new ArrayList<String>();
                    // Correct determinant endings
                    correctlyTranslatedDeterminant.add(determinantCorrecter(i, translationWords, translationExactWords, false)); // Find correct Spanish determinant
                    possibleCorrectAnswer.add(correctlyTranslatedDeterminant.get(0));
                    // See if corrected determinant appears anywhere in user's submission
                    int[] determinantResultArray = containsPhrase(correctlyTranslatedDeterminant, submittedWords);
                    if (determinantResultArray[0] == 1) {
                        submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // See if used the alternative to "unos" of "algunos"
                    if (translationExactWords.get(i).equals("some") && translationWords.get(i+1).getGender() == 'f'){
                        correctlyTranslatedDeterminant = new ArrayList<String>();
                        correctlyTranslatedDeterminant.add("algunas");
                        determinantResultArray = containsPhrase(correctlyTranslatedDeterminant, submittedWords);
                        if (determinantResultArray[0] == 1) {
                            submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                        }

                    }
                    if (translationExactWords.get(i).equals("some") && translationWords.get(i+1).getGender() == 'm'){
                        correctlyTranslatedDeterminant = new ArrayList<String>();
                        correctlyTranslatedDeterminant.add("algunos");
                        determinantResultArray = containsPhrase(correctlyTranslatedDeterminant, submittedWords);
                        if (determinantResultArray[0] == 1) {
                            submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                        }

                    }
                    continue;
                }
                if (translationWords.get(i).getWordTopic().contains("Adjectives")) { // in the case it is an adjective
                    boolean plural = adjectivePlurals.get(0);
                    char gender = adjectiveGenders.get(0);
                    ArrayList<String> correctedAdjective = new ArrayList<String>();
                    ArrayList<String> alternateCorrectedAdjective = new ArrayList<String>(); // Used when there is more than 1 correct answer (eg gender could be either)
                    // add here if either gender is ok
                    if (adjectiveGenderIrrelevant.get(0) == true){
                        // Correct adjective to agree with noun but allow either gender
                        correctedAdjective.add(adjectiveConjugator(plural, 'm', translationWords.get(i), false));
                        alternateCorrectedAdjective.add(adjectiveConjugator(plural, 'f', translationWords.get(i), false));
                        adjectiveGenderIrrelevant.remove(0);
                    }
                    else {
                        // Correct adjective to agree with noun in a case where gender matters
                        correctedAdjective.add(adjectiveAgree(i, translationWords, translationExactWords, false, false));
                    }
                    possibleCorrectAnswer.add(correctedAdjective.get(0));
                    // See if corrected adjective matches a user's submitted word
                    int[] adjectiveResultArray = containsPhrase(correctedAdjective, submittedWords);
                    if (adjectiveResultArray[0] == 1) {
                        submittedWords = removeWords(0, submittedWords, adjectiveResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    else if (!alternateCorrectedAdjective.isEmpty()){
                        adjectiveResultArray = containsPhrase(alternateCorrectedAdjective, submittedWords);
                        if (adjectiveResultArray[0] == 1){
                            submittedWords = removeWords(0, submittedWords, adjectiveResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                        }
                    }
                    // if didn't match main translation, check list of alternate and see if any of those match
                    for (int k = 0; k < translationWords.get(i).getAlternateSpanishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateSpanishTranslations().get(k);
                        if (currentWord.equals("")){
                            break;
                        }
                        currentWord = adjectiveSynonymConjugator(plural, gender, currentWord);
                        numberOfSpaces = countSpaces(currentWord);
                        ArrayList<String> adjectiveSynonym = new ArrayList<String>();
                        adjectiveSynonym.add(currentWord);
                        int[] alternateResultArray = (containsPhrase(adjectiveSynonym, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // If found the word which appears in user's words, don't continue
                        }
                    }
                    continue;

                }
                if (translationWords.get(i).getWordTopic().contains("Verbs")) { // if word is a verb
                    ArrayList<String> correctedVerb = new ArrayList<String>();
                    String currentTense = verbTenses.get(0);
                    int currentPerson = verbPersons.get(0);
                    // Remove first element from these lists and use to conjugate this verb
                    verbTenses.remove(0);
                    verbPersons.remove(0);
                    correctedVerb.add(verbConjugator(translationWords.get(i), false, currentTense, currentPerson, false, false));
                    ArrayList<String> correctedVerbSplit = phraseSplitter(correctedVerb.get(0));
                    for (int j = 0; j < correctedVerbSplit.size(); j++) {
                        possibleCorrectAnswer.add(correctedVerbSplit.get(j));
                        correctedVerbSplit.set(j, correctedVerbSplit.get(j).toLowerCase());
                    }
                    // Check if verb matches a submitted word
                    int[] verbResultArray = containsPhrase(correctedVerbSplit, submittedWords);
                    if (verbResultArray[0] == 1) {
                        submittedWords = removeWords(correctedVerbSplit.size() - 1, submittedWords, verbResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // In the complex case of the verb "to be", check list of alternative irregular conjugations for a match
                    if (translationWords.get(i).getWord().equals("ser") && currentTense.equals("present")){
                        if (serIrregularSynonyms(translationWords, currentPerson, i, "presentSpanishPronounsMale", submittedWords)){
                            submittedWords = removeWords(correctedVerbSplit.size() - 1, submittedWords, verbResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            continue;
                        }
                        if (serIrregularSynonyms(translationWords, currentPerson, i, "presentSpanishPronounsFemale", submittedWords)){
                            submittedWords = removeWords(correctedVerbSplit.size() - 1, submittedWords, verbResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            continue;
                        }
                        // if didn't match main translation, check list of alternates for a match
                        for (int k = 0; k < translationWords.get(i).getAlternateSpanishTranslations().size(); k++) {
                            currentWord = translationWords.get(i).getAlternateSpanishTranslations().get(k);
                            currentWord = verbConjugator(currentWord, false, currentTense, currentPerson, false, false);
                            numberOfSpaces = countSpaces(currentWord);
                            ArrayList<String> adjectiveSynonym = new ArrayList<String>();
                            adjectiveSynonym.add(currentWord);
                            int[] alternateResultArray = (containsPhrase(adjectiveSynonym, submittedWords));
                            if (alternateResultArray[0] == 1) {
                                submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                                translationExactWordsCopy.add(i, "");
                                translationExactWordsCopy.remove(i+1);
                                break; // If found the word which appears in user's words, don't continue
                            }
                        }
                    }
                    continue;
                }
                // For a general word/phrase
                ArrayList<String> individualWords = phraseSplitter(currentWord);
                for (int j = 0; j < individualWords.size(); j++) {
                    possibleCorrectAnswer.add(individualWords.get(j));
                }
                // Check if user's submission contains the word/phrae
                int[] resultArray = (containsPhrase(individualWords, submittedWords));
                if (resultArray[0] == 1) {
                    // Remove the words from user's translation if they fully match an entire phrase translated correctly
                    submittedWords = removeWords(numberOfSpaces, submittedWords, resultArray[1]);
                    translationExactWordsCopy.add(i, "");
                    translationExactWordsCopy.remove(i+1);
                } else {
                    // if didn't match main translation, check list of alternate
                    for (int k = 0; k < translationWords.get(i).getAlternateSpanishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateSpanishTranslations().get(k);
                        // split each alt translation into indiivudla words
                        ArrayList<String> alternateIndividualWords = phraseSplitter(currentWord);
                        numberOfSpaces = countSpaces(currentWord);
                        int[] alternateResultArray = (containsPhrase(alternateIndividualWords, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // If found the word which appears in user's words, don't continue
                        }
                    }
                }
            }
        } else {
            // Each word, see if the correct form of the translated word finds a match with a user's word
            for (int i = 0; i < translationWords.size(); i++) {
                String currentWord = translationWords.get(i).getMainTranslation();
                int numberOfSpaces = countSpaces(currentWord);
                if (translationWords.get(i).getWordTopic().contains("Nouns")) { // in the case it is a noun
                    ArrayList<String> correctlyTranslatedNoun = new ArrayList<String>();
                    // Return correct English Noun
                    correctlyTranslatedNoun.add(nounCorrecter(false, translationWords.get(i), translationExactWords.get(i)));
                    possibleCorrectAnswer.add(correctlyTranslatedNoun.get(0));
                    // See if user's submission contains the noun, and remove it from list if it does
                    int[] determinantResultArray = containsPhrase(correctlyTranslatedNoun, submittedWords);
                    if (determinantResultArray[0] == 1) {
                        submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // if didn't match main translation, check list of alternate
                    for (int k = 0; k < translationWords.get(i).getAlternateEnglishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateEnglishTranslations().get(k);
                        currentWord =  nounCorrecterSynonym(false, translationWords.get(i), translationExactWords.get(i), currentWord);
                        numberOfSpaces = countSpaces(currentWord);
                        ArrayList<String> adjectiveSynonym = new ArrayList<String>();
                        adjectiveSynonym.add(currentWord);
                        int[] alternateResultArray = (containsPhrase(adjectiveSynonym, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // If found the word which appears in user's words, don't continue
                        }
                    }
                    continue;
                }
                if (translationWords.get(i).getWordTopic().contains("Determinants")) { // In the case it is a determinant
                    ArrayList<String> correctlyTranslatedDeterminant = new ArrayList<String>();
                    // Form correct determinant
                    correctlyTranslatedDeterminant.add(determinantCorrecter(i, translationWords, translationExactWords, true)); // Find correct English determinant
                    possibleCorrectAnswer.add(correctlyTranslatedDeterminant.get(0));
                    // Check for match with user's submitted words
                    int[] determinantResultArray = containsPhrase(correctlyTranslatedDeterminant, submittedWords);
                    if (determinantResultArray[0] == 1) {
                        submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // If introductory singular determinant, accept "a" or "an"
                    else if (correctlyTranslatedDeterminant.get(0).equals("a")){
                        correctlyTranslatedDeterminant.clear();
                        correctlyTranslatedDeterminant.add("an");
                        determinantResultArray = containsPhrase(correctlyTranslatedDeterminant, submittedWords);
                        // Try to find a match with user's submitted words
                        if (determinantResultArray[0] == 1) {
                            submittedWords = removeWords(0, submittedWords, determinantResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                        }
                    }
                    continue;
                }
                if (translationWords.get(i).getWordTopic().contains("Adjectives")){
                    adjectiveGenders.remove(0);
                    adjectivePlurals.remove(0); // This doesnt matter as english adjectives don't conjugate
                    adjectiveGenderIrrelevant.remove(0);
                }
                if (translationWords.get(i).getWordTopic().contains("Verbs")) { // Word is a verb
                    ArrayList<String> correctedVerb = new ArrayList<String>();
                    String currentTense = verbTenses.get(0);
                    int currentPerson = verbPersons.get(0);
                    verbTenses.remove(0);
                    verbPersons.remove(0);
                    boolean needNoun = true;
                    for (int j = i; j >= 0; j--) {
                        // See if there is a noun previously (if not substitute in a pronoun)
                        if (translationWords.get(j).getWordTopic().contains("Nouns")) {
                            needNoun = false;
                        }
                    }
                    // Conjugate verb depending on tense and person stored in lists, and whether there is a nearby verb
                    correctedVerb.add(verbConjugator(translationWords.get(i), true, currentTense, currentPerson, needNoun, false));
                    ArrayList<String> correctedVerbSplit = phraseSplitter(correctedVerb.get(0));
                    for (int j = 0; j < correctedVerbSplit.size(); j++) {
                        possibleCorrectAnswer.add(correctedVerbSplit.get(j));
                        correctedVerbSplit.set(j, correctedVerbSplit.get(j).toLowerCase());
                    }
                    // Try to find match with submitted words
                    int[] verbResultArray = containsPhrase(correctedVerbSplit, submittedWords);
                    if (verbResultArray[0] == 1) {
                        submittedWords = removeWords(correctedVerbSplit.size() - 1, submittedWords, verbResultArray[1]);
                        translationExactWordsCopy.add(i, "");
                        translationExactWordsCopy.remove(i+1);
                    }
                    // if didn't match main translation, check list of alternates
                    for (int k = 0; k < translationWords.get(i).getAlternateEnglishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateEnglishTranslations().get(k);
                        currentWord = verbConjugator(currentWord, true, currentTense, currentPerson, needNoun, false);
                        numberOfSpaces = countSpaces(currentWord);
                        ArrayList<String> adjectiveSynonym = new ArrayList<String>();
                        adjectiveSynonym.add(currentWord);
                        // try to find a match with a submitted word for each alternate
                        int[] alternateResultArray = (containsPhrase(adjectiveSynonym, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // If found the word which appears in user's words, don't continue
                        }
                    }
                    continue;
                }
                // If normal word or phrase
                ArrayList<String> individualWords = phraseSplitter(currentWord);
                for (int j = 0; j < individualWords.size(); j++) {
                    possibleCorrectAnswer.add(individualWords.get(j));
                }
                // Try to find match with submitted words
                int[] resultArray = (containsPhrase(individualWords, submittedWords));
                if (resultArray[0] == 1) {
                    submittedWords = removeWords(numberOfSpaces, submittedWords, resultArray[1]);
                    translationExactWordsCopy.add(i, "");
                    translationExactWordsCopy.remove(i+1);
                } else {
                    // If no match, try to find match with list of alternate translations
                    for (int k = 0; k < translationWords.get(i).getAlternateEnglishTranslations().size(); k++) {
                        currentWord = translationWords.get(i).getAlternateEnglishTranslations().get(k);
                        ArrayList<String> alternateIndividualWords = phraseSplitter(currentWord);
                        numberOfSpaces = countSpaces(currentWord);
                        int[] alternateResultArray = (containsPhrase(alternateIndividualWords, submittedWords));
                        if (alternateResultArray[0] == 1) {
                            submittedWords = removeWords(numberOfSpaces, submittedWords, alternateResultArray[1]);
                            translationExactWordsCopy.add(i, "");
                            translationExactWordsCopy.remove(i+1);
                            break; // Don't keep going if found match
                        }
                    }

                }

            }
            //continue
        }
        boolean everyWordTranslated = true; // Ensure user has correctly translated every word in the question
        for (int i = 0; i < translationExactWordsCopy.size(); i++){
            if (!(translationExactWordsCopy.get(i).equals(""))) {
                everyWordTranslated = false;
            }
        }
        // Then user got translation correct so return empty
        if (submittedWords.isEmpty() && everyWordTranslated) { // Also ensure user hasn't included any unnecessary words
            possibleCorrectAnswer.clear();
            return possibleCorrectAnswer;
        } else {
            // User got translation incorrect, return a possible correct answer to display to user
            possibleCorrectAnswer = wordOrderCorrecter(translationWords, possibleCorrectAnswer);
            System.out.println(possibleCorrectAnswer);
            return possibleCorrectAnswer;
        }
    }

    // Count how many spaces appear in a Word object (which may be a phrase)
    public int countSpaces(String phrase) {
        int numberOfSpaces = 0;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == ' ') {
                numberOfSpaces++;
            }
        }
        return numberOfSpaces;
    }

    // Remove n + 1 consecutive words where n is number of spaces in a word/phrase
    public ArrayList<String> removeWords(int numberOfSpaces, ArrayList<String> wordList, int startingIndex) {
        for (int i = 0; i < numberOfSpaces + 1; i++) {
            wordList.remove(startingIndex);
        }
        return wordList;
    }

    // Splits a multi word phrase into individual words
    public ArrayList<String> phraseSplitter(String phrase) {
        String[] submittedWordsArray = phrase.split(" ");
        ArrayList<String> returnedWords = new ArrayList<String>();
        for (int i = 0; i < submittedWordsArray.length; i++) {
            if (submittedWordsArray[i].trim().length() == 0){ // If string is only whitespace (e.g. a double space) dont include it
                continue;
            }
            else {
                returnedWords.add(submittedWordsArray[i]); // Split input into individual words and add to ArrayList
            }
        }
        return returnedWords;
    }

    // Checks whether a set of words contains a particular word/set of words in correct order
    public int[] containsPhrase(ArrayList<String> phrase, ArrayList<String> submittedWords) {
        int startingIndex = -1;
        int counter = 0;
        for (int j = 0; j < submittedWords.size(); j++) { // Try consecutive words starting at each
            startingIndex = j;
            if (j + phrase.size() > submittedWords.size()) {
                // If there aren't enough spaces left in the array for it to be possible for it to be right, end
                return new int[]{0, -1};
            } else {
                counter = 0;
                for (int k = 0; k < phrase.size(); k++) {
                    if (submittedWords.get(startingIndex).equals(phrase.get(k))) {
                        // if counter equals phrase.size, it has located consecutive words for whoel phrase
                        if (counter == (phrase.size() - 1)) {
                            // Return first element 1 if found a match, 0 if not, return starting index of phrase in second element
                            return new int[]{1, startingIndex - phrase.size() + 1};
                        } else {
                            counter++;
                            startingIndex++;
                            continue; // check if individual word's translation features in user's words, and if it does check for consecutive words
                        }
                    } else
                        break;
                }
            }
        }
        // Return 0 if phrase was not found, with a garbage value of -1 for the index as second element
        return new int[]{0, -1};
    }

    // used to produce correct form of verb
    public String verbConjugator(Word verb, boolean returnEnglish, String tense, int person, boolean needNoun, boolean initialTranslation) {
        if (initialTranslation) { // Only add these when this is the program's phrase, not when translating program's phrase
            verbTenses.add(tense);
            verbPersons.add(person); // Keep track of tense and person for later use when checking user conjugated their verb correctly
        }
        String conjugation = "";
        int infinitiveLength = verb.getInfinitive().length();
        char infinitiveType = verb.getInfinitive().charAt(infinitiveLength - 2);
        if (!returnEnglish) { // Wanting to return Spanish
            if (!(verb.getIrregularConjugations().get(tense + "Spanish") == null)) { // If there are irregular conjugations, use that
                conjugation = verb.getIrregularConjugations().get(tense + "Spanish").get(person - 1);
                return conjugation;
            } else {
                // If not, use regular endings for particular tense
                switch (tense) {
                    case "present":
                        switch (person) {

                            case 1:
                                conjugation = verb.getStem() + "o";
                                return conjugation;

                            case 2:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.getStem() + "as";
                                } else conjugation = verb.getStem() + "es";
                                return conjugation;

                            case 3:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.getStem() + "a";
                                } else conjugation = verb.getStem() + "e";
                                return conjugation;

                            case 4:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.getStem() + "amos";
                                } else if (infinitiveType == 'e') {
                                    conjugation = verb.getStem() + "emos";
                                } else conjugation = verb.getStem() + "imos";
                                return conjugation;

                            case 5:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.getStem() + "áis";
                                } else if (infinitiveType == 'e') {
                                    conjugation = verb.getStem() + "éis";
                                } else conjugation = verb.getStem() + "ís";
                                return conjugation;

                            case 6:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.getStem() + "an";
                                } else conjugation = verb.getStem() + "en";
                                return conjugation;
                        }
                    case "preterite":
                    switch (person) {

                        case 1:
                            if (infinitiveType == 'a') {
                                conjugation = verb.getStem() + "é";
                            } else {
                                conjugation = verb.getStem() + "í";
                            }
                            return conjugation;

                        case 2:
                            if (infinitiveType == 'a'){
                                conjugation = verb.getStem() + "aste";
                            } else {
                                conjugation = verb.getStem() + "iste";
                            }
                            return conjugation;

                        case 3:
                            if (infinitiveType == 'a'){
                                conjugation = verb.getStem() + "ó";
                            } else {
                                conjugation = verb.getStem() + "ió";
                            }
                            return conjugation;

                        case 4:
                            if (infinitiveType == 'a'){
                                conjugation = verb.getStem() + "amos";
                            } else {
                                conjugation = verb.getStem() + "imos";
                            }
                            return conjugation;

                        case 5:
                            if (infinitiveType == 'a'){
                                conjugation = verb.getStem() + "asteis";
                            } else {
                                conjugation = verb.getStem() + "isteis";
                            }
                            return conjugation;

                        case 6:
                            if (infinitiveType == 'a'){
                                conjugation = verb.getStem() + "aron";
                            } else {
                                conjugation = verb.getStem() + "ieron";
                            }
                            return conjugation;

                    }
                    case "imperfect":
                        break;
                    default:
                }
            }
        } else { // Wanting to return English
            if (needNoun) { // If there's no noun in the sentence and so it needs a pronoun also
                switch (person) {
                    case 1:
                        conjugation = "I ";
                        break;
                    case 2:
                        conjugation = "you ";
                        break;
                    case 3:
                        int random = (randomGenerator.nextInt(2)); // Randomise between he and she
                        if (random == 0) {
                            conjugation = "he ";
                        } else conjugation = "she ";
                        break;
                    case 4:
                        conjugation = "we ";
                        break;
                    case 5:
                        if (initialTranslation) {
                            conjugation = "you (plural) "; // To let user know it wants the plural version
                        }
                        else conjugation = "you ";
                        break;
                    case 6:
                        conjugation = "they ";
                        break;
                }
            }
            if (!(verb.getIrregularConjugations().get(tense + "English") == null)) { // Use irregular conjugations if available
                conjugation = conjugation + verb.getIrregularConjugations().get(tense + "English").get(person - 1);
                return conjugation;
            } else { // Regular conjugations (when there are no irregulars)
                switch (tense) {
                    case "present": // Same for all persons in present tense, other than 3rd person
                        switch (person) {
                            case 3:
                                conjugation = conjugation + verb.getMainTranslation() + "s";
                                return conjugation;
                            default:
                                conjugation = conjugation + verb.getMainTranslation();
                                return conjugation; // verb remains same except for he/she/it
                        }
                    case "preterite":
                        conjugation = conjugation + verb.getMainTranslation() + "ed";
                        break;
                    case "imperfect":
                        break;
                    default:
                }
            }
        }
        return conjugation;
    }

    // Verb conjugator for alternate translations using only a string rather than a full Word object
    public String verbConjugator(String verb, boolean returnEnglish, String tense, int person, boolean needNoun, boolean initialTranslation) {
        if (initialTranslation) { // Only add these when this is the program's phrase, not when translating program's phrase
            verbTenses.add(tense);
            verbPersons.add(person); // Keep track of tense and person for later use when checking user conjugated their verb correctly
        }
        String conjugation = "";
        int infinitiveLength = verb.length();
        char infinitiveType = verb.charAt(infinitiveLength - 2);
        if (!returnEnglish) { // Wanting to return Spanish, have to assume verb conjugates regularly
                switch (tense) {
                    case "present":
                        switch (person) {

                            case 1:
                                conjugation = verb.substring(0, infinitiveLength - 2) + "o";
                                return conjugation;

                            case 2:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "as";
                                } else conjugation = verb.substring(0, infinitiveLength - 2) + "es";
                                return conjugation;

                            case 3:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "a";
                                } else conjugation = verb.substring(0, infinitiveLength - 2) + "e";
                                return conjugation;

                            case 4:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "amos";
                                } else if (infinitiveType == 'e') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "emos";
                                } else conjugation = verb.substring(0, infinitiveLength - 2) + "imos";
                                return conjugation;

                            case 5:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "áis";
                                } else if (infinitiveType == 'e') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "éis";
                                } else conjugation = verb.substring(0, infinitiveLength - 2) + "ís";
                                return conjugation;

                            case 6:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "an";
                                } else conjugation = verb.substring(0, infinitiveLength - 2) + "en";
                                return conjugation;
                        }
                    case "preterite":
                        switch (person) {

                            case 1:
                                if (infinitiveType == 'a') {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "é";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "í";
                                }
                                return conjugation;

                            case 2:
                                if (infinitiveType == 'a'){
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "aste";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "iste";
                                }
                                return conjugation;

                            case 3:
                                if (infinitiveType == 'a'){
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "ó";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "ió";
                                }
                                return conjugation;

                            case 4:
                                if (infinitiveType == 'a'){
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "amos";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "imos";
                                }
                                return conjugation;

                            case 5:
                                if (infinitiveType == 'a'){
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "asteis";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "isteis";
                                }
                                return conjugation;

                            case 6:
                                if (infinitiveType == 'a'){
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "aron";
                                } else {
                                    conjugation = verb.substring(0, infinitiveLength - 2) + "ieron";
                                }
                                return conjugation;

                        }
                    case "imperfect":
                        break;
                    default:
                }
            }
         else { // Wanting to return English, have to assume verb conjugates regularly
            if (needNoun) { // If there's no noun in the sentence and so it needs a pronoun also
                switch (person) {
                    case 1:
                        conjugation = "I ";
                        break;
                    case 2:
                        conjugation = "you ";
                        break;
                    case 3:
                        int random = (randomGenerator.nextInt(2)); // Randomise between he and she
                        if (random == 0) {
                            conjugation = "he ";
                        } else conjugation = "she ";
                        break;
                    case 4:
                        conjugation = "we ";
                        break;
                    case 5:
                        if (initialTranslation) {
                            conjugation = "you (plural) "; // To let user know it wants the plural version
                        }
                        else conjugation = "you ";
                        break;
                    case 6:
                        conjugation = "they ";
                        break;
                }
            }
         else { // Regular conjugations
                switch (tense) {
                    case "present": // Same for all persons in present tense, other than 3rd person
                        switch (person) {
                            case 3:
                                conjugation = conjugation + verb + "s";
                                return conjugation;
                            default:
                                conjugation = conjugation + verb;
                                return conjugation; // verb remains same except for he/she/it
                        }
                    case "preterite":
                        conjugation = conjugation + verb + "ed";
                        break;
                    case "imperfect":
                        break;
                    default:
                }
            }
        }
        return conjugation;
    }

    // Check whether phrase contains specific alternative for the verb "to be"
    public boolean serIrregularSynonyms(ArrayList<Word> translationWords, int person, int index, String irregularType, ArrayList<String> submittedWords){
        ArrayList<String> correctedVerbSplit = phraseSplitter(translationWords.get(index).getIrregularConjugations().get(irregularType).get(person - 1));
        int[] verbResultArray = containsPhrase(correctedVerbSplit, submittedWords);
        if (verbResultArray[0] == 1) {
            return true;
        }
        return false;
    }

    // Forms correct version of determinant
    public String determinantCorrecter(int determinantIndex, ArrayList<Word> currentWords, ArrayList<String> currentExactWords, boolean returnEnglish) {
        for (int i = determinantIndex + 1; i < currentWords.size(); i++)
            if (currentWords.get(i).getWordTopic().contains("Nouns")) { // Find nearest noun following the determinant
                char gender = currentWords.get(i).getGender();
                boolean plural;
                if (currentWords.get(i).getSpanishPlural().equals(currentExactWords.get(i)) || currentWords.get(i).getEnglishPlural().equals((currentExactWords.get(i)))) {
                    plural = true; // check if plural
                } else plural = false;

                char nextletter = currentExactWords.get(determinantIndex + 1).charAt(0);

                if (plural) { // This segment deals with plural determinants
                    if (returnEnglish) {
                        return currentWords.get(determinantIndex).getEnglishPlural(); // Replace 'a' with 'some' if plural
                    } else if (gender == 'f') {
                        return currentWords.get(determinantIndex).getAlternateSpanishTranslations().get(2);
                    } else return currentWords.get(determinantIndex).getAlternateSpanishTranslations().get(1);

                    // This segment deals with singular determinants
                } else if (returnEnglish) {
                    // replace 'a' with 'an' if necessary
                    if (nextletter == 'a' || nextletter == 'e' || nextletter == 'i' || nextletter == 'o' || nextletter == 'u') {
                        return currentWords.get(determinantIndex).getAlternateEnglishTranslations().get(0);
                    } else return currentWords.get(determinantIndex).getMainTranslation();

                } else if (gender == 'f') {
                    return currentWords.get(determinantIndex).getAlternateSpanishTranslations().get(0);
                } else {
                    return currentWords.get(determinantIndex).getWord();
                }
            }
        return "";
    }

    // Return correct version of noun, depending on language and whether plural or singular
    public String nounCorrecter(boolean englishToSpanish, Word word, String exactWord){
        if (englishToSpanish){
            if (exactWord.equals(word.getEnglishPlural())){
                return word.getSpanishPlural();
            }
            else return word.getWord();
        }
        else if (exactWord.equals(word.getSpanishPlural())){
            return word.getEnglishPlural();
        }
        else return word.getMainTranslation();
    }

    // Corrects noun for an alternate which doesnt explicitly store a plural version, so assume it is regular
    public String nounCorrecterSynonym(boolean englishToSpanish, Word word, String exactWord, String synonym){
        if (englishToSpanish){
            if (exactWord.equals(word.getEnglishPlural())){
                return synonym + "s";
            }
            else return synonym;
        }
        else if (exactWord.equals(word.getSpanishPlural())){
            return synonym + "s";
        }
        else return synonym;
    }

    // Used as English word order is slightly different from the Spanish default
    public ArrayList<String> wordOrderCorrecter(ArrayList<Word> words, ArrayList<String> exactWords) {
        // swap adjective and previous noun around, however, if previously it finds BeingVerbs before adjective, keep same order
        ArrayList<String> correctedWords = exactWords;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTopic().contains("Adjectives")) {
                for (int j = i; j >= 0; j--) {
                    // swap adjective and noun if english (as structures assume Spanish word order)
                    if (words.get(j).getWordTopic().contains("Nouns")) {
                        String wordHolder = correctedWords.get(j);
                        correctedWords.set(j, correctedWords.get(i));
                        correctedWords.set(i, wordHolder);
                        break;
                    } else if (words.get(j).getWordTopic().contains("BeingVerbs")) {
                        break;
                    }
                }
            }
        }
        return correctedWords;
    }

    // To return corrected word order for just Array List of Words instead
    public ArrayList<Word> wordOrderCorrecter(ArrayList<Word> words) {
        // swap adjective and previous noun around, however, if previously it finds BeingVerbs before adjective, keep
        ArrayList<Word> correctedWords = words;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTopic().contains("Adjectives")) {
                for (int j = i; j >= 0; j--) {
                    // swap adjective and noun if english (as structures assume Spanish word order)
                    if (words.get(j).getWordTopic().contains("Nouns")) {
                        Word wordHolder = correctedWords.get(j);
                        correctedWords.set(j, correctedWords.get(i));
                        correctedWords.set(i, wordHolder);
                        break;
                    } else if (words.get(j).getWordTopic().contains("BeingVerbs")) {
                        break;
                    }
                }
            }
        }
        return correctedWords;
    }

    // Transforms actual adjective into conjugated version using plural and gender
    public String adjectiveConjugator (boolean plural, char gender, Word adjective, boolean englishAdjective){
        if (englishAdjective){
            return adjective.getMainTranslation(); // English adjectives dont conjugate
        }
        String correctedAdjective;
        if (plural){
            correctedAdjective = adjective.getSpanishPlural();
            int length = correctedAdjective.length();
            // Ensure correct plural ending depending on gender
            if (gender == 'f'){
                if (correctedAdjective.substring(length - 2, length).equals("os")){
                    correctedAdjective = correctedAdjective.substring(0, length - 2) + "as";
                }
            }
            return correctedAdjective;
        }
        else {
            correctedAdjective = adjective.getWord();
            int length = correctedAdjective.length();
            // Ensure correct singular ending depending on gender
            if (gender == 'f'){
                if (correctedAdjective.substring(length - 1, length).equals("o")){
                    correctedAdjective = correctedAdjective.substring(0, length - 1) + "a";
                }
            }
            return correctedAdjective;
        }
    }

    // Transform alternate adjective into conjugated version using plural and gender
    public String adjectiveSynonymConjugator (boolean plural, char gender, String adjective){
        String correctedAdjective = adjective;
        int length = adjective.length();
        if (plural){
            // assume adjective changes ending regularly, e.g., if ends in o then change to "as" for female plural, or add s for male plural
            if ((adjective.charAt(length - 1) == 'o')){
                if (gender == 'f'){
                    correctedAdjective = adjective.substring(0, length - 1) + "as";
                    return correctedAdjective;
                }
                else{
                    correctedAdjective = correctedAdjective + "s";
                    return correctedAdjective;
                }
            }
            // Else assume plural just add s
            else {
                correctedAdjective =  correctedAdjective + "s";
                return correctedAdjective;
            }
        }
        else {
            // If ends in o, assume this changes to a if feminine
            if ((adjective.charAt(length - 1) == 'o')) {
                if (gender == 'f') {
                    correctedAdjective = adjective.substring(0, length - 1) + "a";
                    return correctedAdjective;
                }
            }
            return correctedAdjective;
        }

    }

    // only called for Spanish as english adjectives dont have to agree with noun
    public String adjectiveAgree(int adjectiveIndex, ArrayList<Word> currentWords, ArrayList<String> currentExactWords, boolean initialTranslation, boolean englishAdjective) { // only for spanish Toenglish, check for noun or being verb and agree to that
        boolean plural = false;
        char gender = 'm';
        if (!initialTranslation){
            // Utilise lists of variables if not initial
            boolean currentPlural = adjectivePlurals.get(0);
            char currentGender = adjectiveGenders.get(0);
            adjectivePlurals.remove(0);
            adjectiveGenders.remove(0);
            adjectiveGenderIrrelevant.remove(0);
            // Format ending based on these variables
            String correctedAdjective = adjectiveConjugator(currentPlural, currentGender, currentWords.get(adjectiveIndex), englishAdjective);
            return correctedAdjective;
        }
        String correctedAdjective;
        for (int i = adjectiveIndex; i >= 0; i--) {
            if (currentWords.get(i).getWordTopic().contains("Nouns")) { // When adjective agrees to previous noun used
                gender = currentWords.get(i).getGender();
                // Add variables to necessary lists of plurals, genders, and whether gender matters
                // Find out information about previous noun, and use this determine how adjective should be
                if (currentWords.get(i).getSpanishPlural().equals(currentExactWords.get(i))) {
                    plural = true;
                    correctedAdjective = adjectiveConjugator(plural, gender, currentWords.get(adjectiveIndex), englishAdjective);
                    adjectivePlurals.add(plural);
                    adjectiveGenders.add(gender);
                    adjectiveGenderIrrelevant.add(false);
                    return correctedAdjective;
                }
                else if (currentWords.get(i).getEnglishPlural().equals(currentExactWords.get(i))) {
                    adjectivePlurals.add(true);
                    adjectiveGenders.add(gender);
                    adjectiveGenderIrrelevant.add(false);
                    return currentExactWords.get(adjectiveIndex);
                }
                else {
                    plural = false;
                    correctedAdjective = adjectiveConjugator(plural, gender, currentWords.get(adjectiveIndex), englishAdjective);
                    adjectivePlurals.add(plural);
                    adjectiveGenders.add(gender);
                    adjectiveGenderIrrelevant.add(false);
                    return correctedAdjective;
                }
            }
        }
        for (int i = adjectiveIndex; i >= 0; i--) {  // only go to being verbs if there are no nouns (E.g. I am happy has no noun)
            if (currentWords.get(i).getWordTopic().contains("BeingVerbs")) { // see if plural then randomise gender
                if (currentExactWords.get(i).contains(" he ")){ // Ensure if gender is specified in pronoun, it carries forward
                    gender = 'm';
                    adjectiveGenderIrrelevant.add(false);
                }
                else if (currentExactWords.get(i).contains(" she ")){
                    gender = 'f';
                    adjectiveGenderIrrelevant.add(false);
                }
                else {
                    adjectiveGenderIrrelevant.add(true);
                    int random = randomGenerator.nextInt(2);
                    if (random == 0) {
                        gender = 'm';
                    } else gender = 'f';
                }
                // Special case for you plural
                if (currentExactWords.get(i).equals("you (plural) are")){
                    adjectivePlurals.add(true);
                    adjectiveGenders.add(gender);
                    plural = true;
                    correctedAdjective = adjectiveConjugator(plural, gender, currentWords.get(adjectiveIndex), englishAdjective);
                    return correctedAdjective;
                }
                for (String word : currentWords.get(i).getIrregularConjugations().keySet()) {
                    for (int j = 3; j < 6; j++) { // Check only plural persons of verb to see if plural or not
                        if (currentExactWords.get(i).toLowerCase().equals(currentWords.get(i).getIrregularConjugations().get(word).get(j))){
                            // Then the word is plural
                            adjectivePlurals.add(true);
                            adjectiveGenders.add(gender);
                            plural = true;
                            correctedAdjective = adjectiveConjugator(plural, gender, currentWords.get(adjectiveIndex), englishAdjective);
                            return correctedAdjective;
                        }
                    }
                }
                // Then word is singular
                plural = false;
                adjectivePlurals.add(false);
                adjectiveGenders.add(gender);
                correctedAdjective = adjectiveConjugator(plural, gender, currentWords.get(adjectiveIndex), englishAdjective);
                return correctedAdjective;
            }
        }
        return "";
    }


}
