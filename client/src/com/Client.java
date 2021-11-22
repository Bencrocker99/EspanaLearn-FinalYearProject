package src.com;// add packages at top of files


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

//public class Client implements Initializable {

public class Client {

    private JSONFileReader jsonFileReader;
    private static int highestScore;
    private static HashMap<Integer, Level> levelsHashMap = new HashMap<Integer, Level>();
    private static HashMap<String, Topic> topicsHashMap = new HashMap<String, Topic>();
    private static ArrayList<Topic> topicsList = new ArrayList<Topic>();
    public static Client client = null;
    private static int currentEndlessLives;
    private static int currentEndlessScore;
    private static int currentEndlessLevel;
    private static int practiceCurrentStreak;
    private static boolean practiceIncludeBossLevels;
    private static boolean practiceNoBossLevels;
    private static boolean practiceOnlyBossLevels;
    private static boolean practiceEnglishToSpanishOnly;
    private static boolean practiceSpanishToEnglishOnly;
    private static boolean practiceBothLanguages;
    private static boolean practiceAdaptiveMistakesMode;
    private static ArrayList<Level> practicePossibleLevels = new ArrayList<Level>();
    private static boolean currentlyPractising;
    private static boolean needPracticePopup;
    private static int totalScore;
    private static int highestLevel;
    private static int practiceHighestStreak;
    private static double soundEffectsVolume;
    private static final int PORT_NUMBER = 6666;
    private static Socket socket;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;
    private static HashMap<String, Integer> achievementsProgress = new HashMap<String, Integer>();
    private static ArrayList<Achievement> achievementsList = new ArrayList<Achievement>();
    private static MediaPlayer incorrectSound;
    private static MediaPlayer gameoverSound;
    private static MediaPlayer clickSound;
    private static MediaPlayer correctSound;
    private static MediaPlayer musicPlayer;

    public Client(Socket socket, BufferedReader bufferedReader, PrintWriter printWriter) throws JSONException, IOException, AWTException, InterruptedException {
        Client.client = this;
        // Read in words and levels
        jsonFileReader = new JSONFileReader();
        topicsHashMap = jsonFileReader.readJsonWordsFile();
        levelsHashMap = jsonFileReader.readJsonLevelsFile();
        topicsList = jsonFileReader.getTopicList();
        // Create socket, bufferedReader and printWriter to communicate with server
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        Client.printWriter = printWriter;
        // Read data from Server
        Client.highestScore = Integer.parseInt(bufferedReader.readLine());
        Client.totalScore = Integer.parseInt(bufferedReader.readLine());
        Client.highestLevel = Integer.parseInt(bufferedReader.readLine());
        Client.soundEffectsVolume = Double.parseDouble(bufferedReader.readLine());
        loadAchievements();
        loadAchievementsProgress();
        // Setup sound media players
        Media correctSoundMedia = new Media(new File("client/src/resources/correct.wav").toURI().toString());
        Media gameoverSoundMedia = new Media(new File("client/src/resources/gameover.wav").toURI().toString());
        Media incorrectSoundMedia = new Media(new File("client/src/resources/incorrect.wav").toURI().toString());
        Media clickSoundMedia = new Media(new File("client/src/resources/click.wav").toURI().toString());
        Media music = new Media(new File("client/src/resources/music.mp3").toURI().toString());
        correctSound = new MediaPlayer(correctSoundMedia);
        gameoverSound = new MediaPlayer(gameoverSoundMedia);
        incorrectSound = new MediaPlayer(incorrectSoundMedia);
        clickSound = new MediaPlayer(clickSoundMedia);
        musicPlayer = new MediaPlayer(music);
        // Adjust volume to user's volume setting saved on server
        changeSoundVolume(soundEffectsVolume);
    }

    // save variables for endless mode if going to a new screen
    public static void savingEndlessVariables(int currentScore, int currentLives, int currentLevel){
        currentEndlessScore = currentScore;
        currentEndlessLives = currentLives;
        currentEndlessLevel = currentLevel;
    }

    // save variables for practice mode if going to a new screen
    public static void savingPracticeVariables(int currentStreak, int highestStreak, boolean englishToSpanishOnly, boolean spanishToEnglishOnly,
                                               boolean bothLanguages, boolean bossLevelsOnly, boolean noBossLevels,
                                               boolean includeBossLevels, ArrayList<Level> possibleLevels, boolean adaptiveMistakesMode){
        practiceCurrentStreak = currentStreak;
        practiceHighestStreak = highestStreak;
        practiceEnglishToSpanishOnly = englishToSpanishOnly;
        practiceSpanishToEnglishOnly = spanishToEnglishOnly;
        practiceBothLanguages = bothLanguages;
        practiceOnlyBossLevels = bossLevelsOnly;
        practiceNoBossLevels = noBossLevels;
        practiceIncludeBossLevels = includeBossLevels;
        practicePossibleLevels = possibleLevels;
        practiceAdaptiveMistakesMode = adaptiveMistakesMode;
    }

    public static void loadAchievementsProgress() throws IOException {
        while (true) {
            String achievementName = bufferedReader.readLine();
            // If server prints "done", it has sent over all achievements' progress
            if (achievementName.equals("done")) {
                return;
            }
            // Update achievement progress for each achievement from server
            String achievementProgress = bufferedReader.readLine();
            achievementsProgress.put(achievementName, Integer.valueOf(achievementProgress));
        }
    }

    public static boolean getCurrentlyPractising () {
        return currentlyPractising;
    }

    public static void setCurrentlyPractising (boolean practising){
        currentlyPractising = practising;
    }


    public static int getPracticeCurrentStreak() {
        return practiceCurrentStreak;
    }

    public static boolean isPracticeIncludeBossLevels() {
        return practiceIncludeBossLevels;
    }

    public static boolean isPracticeNoBossLevels() {
        return practiceNoBossLevels;
    }

    public static boolean isPracticeOnlyBossLevels() {
        return practiceOnlyBossLevels;
    }

    public static boolean isPracticeEnglishToSpanishOnly() {
        return practiceEnglishToSpanishOnly;
    }

    public static boolean isPracticeSpanishToEnglishOnly() {
        return practiceSpanishToEnglishOnly;
    }

    public static boolean isPracticeBothLanguages() {
        return practiceBothLanguages;
    }

    public static boolean isPracticeAdaptiveMistakesMode() {
        return practiceAdaptiveMistakesMode;
    }

    public static ArrayList<Level> getPracticePossibleLevels() {
        return practicePossibleLevels;
    }

    public int getCurrentEndlessLives() {
        return currentEndlessLives;
    }

    public int getCurrentEndlessScore() {
        return currentEndlessScore;
    }

    public int getCurrentEndlessLevel() {
        return currentEndlessLevel;
    }

    public void setCurrentEndlessLives (int lives){
        currentEndlessLives = lives;
    }

    public void setCurrentEndlessScore (int score) {
        currentEndlessScore = score;
    }

    public static HashMap<String, Topic> getTopicsHashMap() {
        return topicsHashMap;
    }

    public static HashMap<Integer, Level> getLevelsHashMap() {
        return levelsHashMap;
    }

    public static ArrayList<Topic> getTopicsList() {
        return topicsList;
    }

    public static boolean getNeedPracticePopup() {
        return needPracticePopup;
    }

    public static void setNeedPracticePopup(boolean practicePopup){
        needPracticePopup = practicePopup;
    }

    public static void setPracticeCurrentStreak(int currentStreak) {
        practiceCurrentStreak = currentStreak;
    }

    public static int getPracticeHighestStreak() {
        return practiceHighestStreak;
    }

    public static int getHighestScore(){
        return highestScore;
    }

    public static int getTotalScore(){
        return totalScore;
    }

    public static int getHighestLevel() {
        return highestLevel;
    }

    public static Socket getSocket(){
        return socket;
    }

    public static BufferedReader getBufferedReader(){
        return bufferedReader;
    }

    // Set highest score here, and on server
    public static void setHighestScore(int score){
        highestScore = score;
        printToServer("setHighScore");
        printToServer(String.valueOf(score));
    }

    // Set total core here, and on server
    public static void setTotalScore(int score){
        totalScore = score;
        printToServer("setTotalScore");
        printToServer(String.valueOf(score));
    }

    // Set highest level here, and on server
    public static void setHighestLevel(int level){
        highestLevel = level;
        printToServer("setHighLevel");
        printToServer(String.valueOf(level));
    }



    public static HashMap<String, Integer> getAchievementsProgress(){
        return achievementsProgress;
    }

    // Print a message to the server
    public static void printToServer(String message){
        System.out.println(message);
        printWriter.println(message);
    }


    public static void loadAchievements() throws IOException, JSONException {
        // Read achievements from json file and save as java objects
        String wordsFilePath = "shared/src/com/achievements.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(wordsFilePath)));
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray achievementsArray = jsonObject.getJSONArray("achievements");
        // Create an achievement object for each achievement in the array with the necessary variables
        for (int i = 0; i < achievementsArray.length(); i++){
            String achievementName = achievementsArray.getJSONObject(i).getString("achievementName");
            int levelNumber = achievementsArray.getJSONObject(i).getInt("levelNumber");
            int achievementNumber = achievementsArray.getJSONObject(i).getInt("achievementNumber");
            String achievementDescription = achievementsArray.getJSONObject(i).getString("achievementDescription");
            int experiencePoints = achievementsArray.getJSONObject(i).getInt("experiencePoints");
            int numberOfTimes = achievementsArray.getJSONObject(i).getInt("numberOfTimes");
            Achievement achievement = new Achievement(achievementName, achievementDescription, experiencePoints, levelNumber, numberOfTimes, achievementNumber);
            achievementsList.add(achievement);
        }
    }

    // Play sound and return to beginning for next time
    public static void playCorrectSound() {
        correctSound.play();
        correctSound.seek(correctSound.getStartTime());
    }

    // Play sound and return to beginning for next time
    public static void playGameoverSound() {
        gameoverSound.play();
        gameoverSound.seek(gameoverSound.getStartTime());
    }

    // Play sound and return to beginning for next time
    public static void playClickSound() {
        clickSound.play();
        clickSound.seek(clickSound.getStartTime());
    }

    // Play sound and return to beginning for next time
    public static void playIncorrectSound() {
        incorrectSound.play();
        incorrectSound.seek(incorrectSound.getStartTime());
    }

    // Update all media players' volume
    public static void changeSoundVolume(double volume) {
        correctSound.setVolume(volume);
        incorrectSound.setVolume(volume);
        clickSound.setVolume(volume);
        gameoverSound.setVolume(volume);
    }

    public static ArrayList<Achievement> getAchievementsList() {
        return achievementsList;
    }

    // Increment an unlockable skip achievement
    public static void incrementSkipAchievement(int levelNumber){
        // Tell server an achievement needs to be incremented
        Client.printToServer("incrementAchievement");

        // Increment achievement here, and tell server to increment it too
        switch (levelNumber){
            case 1: Client.printToServer("1achievement5");
            Client.getAchievementsProgress().put("1achievement5", Client.getAchievementsProgress().get("1achievement5") + 1);
            break;
            case 2: Client.printToServer("2achievement5");
            Client.getAchievementsProgress().put("2achievement5", Client.getAchievementsProgress().get("2achievement5") + 1);
            break;
            case 3: Client.printToServer("3achievement5");
            Client.getAchievementsProgress().put("3achievement5", Client.getAchievementsProgress().get("3achievement5") + 1);
            break;
            case 4: Client.printToServer("4achievement5");
            Client.getAchievementsProgress().put("4achievement5", Client.getAchievementsProgress().get("4achievement5") + 1);
            break;
        }

    }
}
