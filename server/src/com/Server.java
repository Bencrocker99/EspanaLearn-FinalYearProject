package com;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import src.com.Achievement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private static final int PORT_NUMBER = 6666;
    // Mapping the socket a user is currently using to their username
    private static HashMap<Socket, String> currentlyConnected = new HashMap<Socket, String>();
    // Stores every username in the system mapped to their corresponding User class
    private static HashMap<String, User> usersHashMap = new HashMap<String, User>();
    private static ArrayList<Achievement> achievementsList = new ArrayList<Achievement>();
    private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        setupAchievements();
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        // Continually check for any new connections
            Thread thread = checkForConnections(serverSocket);
            thread.start();
    }

    public static Thread checkForConnections(ServerSocket serverSocket) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // Accept any new connections, and create necessary variables to communicate to client over socket
                        Socket socket = serverSocket.accept();
                        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                        // Create a thread to continually check for commands sent from the client
                        Thread thread = createThread(bufferedReader, printWriter, socket);
                        thread.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
return thread;

    }

    public static Thread createThread(BufferedReader bufferedReader, PrintWriter printWriter, Socket socket) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (true) {
                    try {
                        // Continually check to see if received a command from the client
                        if ((message = bufferedReader.readLine()) != null) {
                            System.out.println(message);

                            // Find the correct method to call depending on the command
                            readCommand(message, bufferedReader, printWriter, socket, this);
                            if (message.equals("quit")){
                                break; // Break thread if user has quit
                            }
                        }
                      } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error 1");
                        try {
                            System.out.println("Error 3");
                            socket.close();
                            System.out.println("Error 10000");
                            return;
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }


            }
        });

        return thread;
    }

    // Find correct method depending on the command given by the client
    public static void readCommand(String command, BufferedReader bufferedReader, PrintWriter printWriter, Socket socket, Runnable runnable) throws IOException {
        switch (command) {
            case "setHighScore":
                setHighScore(bufferedReader, socket);
                break;
            case "getHighScore":
                getHighscore(printWriter, socket);
                break;
            case "setHighLevel":
                setHighLevel(bufferedReader, socket);
                break;
            case "getHighLevel":
                getHighLevel(printWriter, socket);
                break;
            case "getTotalScore":
                getTotalScore(printWriter, socket);
                break;
            case "setTotalScore":
                setTotalScore(bufferedReader, socket);
                break;
            case "setSoundEffectsVolume":
                setSoundEffectsVolume(bufferedReader, socket);
                break;
            case "incrementExperience":
                incrementExperiencePoints(bufferedReader, socket);
                break;
            case "getExperience":
                getExperiencePoints(printWriter, socket);
                break;
            case "incrementAchievement":
                incrementAchievement(bufferedReader, socket);
                break;
            case "register":
                registerUser(bufferedReader, printWriter);
                break;
            case "leaderboardByHighestScore":
                leaderboardByHighestScore(printWriter);
                break;
            case "leaderboardByTotalScore":
                leaderboardByTotalScore(printWriter);
                break;
            case "leaderboardByHighestLevel":
                leaderboardByHighestLevel(printWriter);
                break;
            case "login":
                // See whether login has been successful, add user to currentlyConnected and load their data if it has been
                String success = loginUser(bufferedReader, printWriter);
                if (!success.equals("false")){
                    currentlyConnected.put(socket, success);
                    loadUserData(printWriter, success);
                }
                break;
            case "quit":
                // remove socket from currentlyConnected if user has quit the program, and close the socket
                currentlyConnected.remove(socket);
                socket.close();
                break;
        }
    }

    // Update user's endless highscore on the server
    public static void setHighScore(BufferedReader bufferedReader, Socket socket) throws IOException {
        String username = currentlyConnected.get(socket);
        int newScore = Integer.parseInt(bufferedReader.readLine());
        usersHashMap.get(username).setHighestScore(newScore);

    }

    // Send user's endless highscore from the server to client
    public static void getHighscore(PrintWriter printWriter, Socket socket) {
        String username = currentlyConnected.get(socket);
        printWriter.println(usersHashMap.get(username).getHighestScore());
    }

    // Update user's endless high level on the server
    public static void setHighLevel(BufferedReader bufferedReader, Socket socket) throws IOException {
        String username = currentlyConnected.get(socket);
        int newScore = Integer.parseInt(bufferedReader.readLine());
        usersHashMap.get(username).setHighestLevel(newScore);
    }

    // Send user's endless high level from the server to client
    public static void getHighLevel(PrintWriter printWriter, Socket socket) {
        String username = currentlyConnected.get(socket);
        printWriter.println(usersHashMap.get(username).getHighestLevel());
    }

    // Currently unused - send user's experience points from server to client
    public static void getExperiencePoints(PrintWriter printWriter, Socket socket) {
        String username = currentlyConnected.get(socket);
        printWriter.println(usersHashMap.get(username).getExperiencePoints());
    }

    // Send user's total endless score from the server to client
    public static void getTotalScore(PrintWriter printWriter, Socket socket){
        String username = currentlyConnected.get(socket);
        printWriter.println(usersHashMap.get(username).getTotalScore());
    }

    // Update user's total endless score on the server
    public static void setTotalScore(BufferedReader bufferedReader, Socket socket) throws IOException {
        String username = currentlyConnected.get(socket);
        int newScore = Integer.parseInt(bufferedReader.readLine());
        usersHashMap.get(username).setTotalScore(newScore);
    }

    // Update user's sound effects volume on the server
    public static void setSoundEffectsVolume(BufferedReader bufferedReader, Socket socket) throws IOException {
        String username = currentlyConnected.get(socket);
        double soundEffectsVolume = Double.parseDouble(bufferedReader.readLine());
        usersHashMap.get(username).setSoundEffectsVolume(soundEffectsVolume);
    }

    // Currently unused - add set amount sent from client to experience points saved on server
    public static void incrementExperiencePoints(BufferedReader bufferedReader, Socket socket) throws IOException {
        String username = currentlyConnected.get(socket);
        usersHashMap.get(username).incrementExperiencePoints(Integer.parseInt(bufferedReader.readLine()));
    }

    // Called when a user tries to create a new account
    public static void registerUser(BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
        String username = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        boolean availableUsername = true;
        // If username has already been used, reject the create account attempt and display error message client side
        for (String existingUsername : usersHashMap.keySet()){
            if (existingUsername.equals(username)){
                availableUsername = false;
            }
        }
        if (availableUsername){
            // Add new username to usersHashMap mapped to a new User class using their username and encrypted password
            usersHashMap.put(username, new User(username, encryptedPassword));
            for (int i = 0; i < achievementsList.size(); i++){
                usersHashMap.get(username).addAchievement(achievementsList.get(i));
            }
            printWriter.println("true");
        }
        else printWriter.println("false");
    }

    // Called when a user tries to login
    public static String loginUser(BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {
        String username = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        // Check whether the submitted password for the username matches the (encrypted) password stored on the server
        if (usersHashMap.get(username) != null) {
            if (passwordEncryptor.checkPassword(password, usersHashMap.get(username).getEncryptedPassword())) {
                printWriter.println("true");
                return username;
            } else {
                printWriter.println("false");
                return "false";
            }
        }
        // Fail to login if submitted username doesn't appear on the server
        else {
            printWriter.println("false");
            return "false";
        }
    }

    // Used to increment a user's progress on a particular achievement within their User class on the server
    public static void incrementAchievement(BufferedReader bufferedReader, Socket socket) throws IOException {
        String achievementName = bufferedReader.readLine();
        String username = currentlyConnected.get(socket);
        usersHashMap.get(username).getAchievementsProgress().put(achievementName, usersHashMap.get(username).getAchievementsProgress().get(achievementName) + 1);
    }

    // Called to setup achievements when server is first run
    public static void setupAchievements() throws IOException, JSONException {
        // Read in achievements from json file and store in achievementsArray
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

    // Called when a user logs in to update the client with all the user's data saved on the server
    public static void loadUserData(PrintWriter printWriter, String username){
        printWriter.println(usersHashMap.get(username).getHighestScore());
        printWriter.println(usersHashMap.get(username).getTotalScore());
        printWriter.println(usersHashMap.get(username).getHighestLevel());
        printWriter.println(usersHashMap.get(username).getSoundEffectsVolume());
        // Update the Client with the user's achievement progress for every achievement
        for (String achievementName: usersHashMap.get(username).getAchievementsProgress().keySet()){ // Give achievement progress
            printWriter.println(achievementName);
            printWriter.println(usersHashMap.get(username).getAchievementsProgress().get(achievementName));
        }
        // Tell client all achievements' progress has been sent over by printing "done"
        printWriter.println("done");
    }

    // Called when user wishes to see the highest score leaderboard
    public static void leaderboardByHighestScore(PrintWriter printWriter){
        ArrayList<String> orderedUsernames = new ArrayList<String>();
        ArrayList<Integer> orderedHighScores = new ArrayList<Integer>();
        // For every user on the server, find their highscore and order them in descending order of highest score
        for (User user: usersHashMap.values()){
            int highScore = user.getHighestScore();
            boolean alreadyAdded = false;
            // Find exact placement of user's highscore, and save their username and highscore in this position
            for (int i = 0; i < orderedUsernames.size(); i++){
                if (highScore > orderedHighScores.get(i)){
                    orderedHighScores.add(i, highScore);
                    orderedUsernames.add(i, user.getUsername());
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) { // If not already added it must be the lowest score out of everyone so put at the end
                orderedHighScores.add(orderedHighScores.size(), highScore);
                orderedUsernames.add(orderedUsernames.size(), user.getUsername());
            }

        }
        // Print the first 50 names and high scores (or however many there are on the server)
        for (int i = 0; i < orderedUsernames.size(); i++) {
            if (i >= 50) {
                break;
            }
            printWriter.println(orderedUsernames.get(i));
            printWriter.println(orderedHighScores.get(i));
        }
        // Print "done" when finished so client know not to expect any more
        printWriter.println("done");
    }

    // Called when user wishes to see the highest level leaderboard
    public static void leaderboardByHighestLevel(PrintWriter printWriter){
        ArrayList<String> orderedUsernames = new ArrayList<String>();
        ArrayList<Integer> orderedHighScores = new ArrayList<Integer>();
        // For every user on the server, find their highest level and order them in descending order of highest leve
        for (User user: usersHashMap.values()){
            int highScore = user.getHighestLevel();
            boolean alreadyAdded = false;
            // Find exact placement of user's highest level, and save their username and highest level in this position
            for (int i = 0; i < orderedUsernames.size(); i++){
                if (highScore > orderedHighScores.get(i)){
                    orderedHighScores.add(i, highScore);
                    orderedUsernames.add(i, user.getUsername());
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) { // If not already added it must be the lowest score out of everyone so put at the end
                orderedHighScores.add(orderedHighScores.size(), highScore);
                orderedUsernames.add(orderedUsernames.size(), user.getUsername());
            }

        }
        // Print the first 50 names and high levels (or however many there are on the server)
        for (int i = 0; i < orderedUsernames.size(); i++) {
            if (i >= 50) {
                break;
            }
            printWriter.println(orderedUsernames.get(i));
            printWriter.println(orderedHighScores.get(i));
        }
        // Print "done" when finished so client know not to expect any more
        printWriter.println("done");
    }

    // Called when user wishes to see the total score leaderboard
    public static void leaderboardByTotalScore(PrintWriter printWriter){
        ArrayList<String> orderedUsernames = new ArrayList<String>();
        ArrayList<Integer> orderedTotalScores = new ArrayList<Integer>();
        // For every user on the server, find their total score and order them in descending order of total score
        for (User user: usersHashMap.values()){
            int totalScore = user.getTotalScore();
            boolean alreadyAdded = false;
            // Find exact placement of user's total score, and save their username and total score in this position
            for (int i = 0; i < orderedUsernames.size(); i++){
                if (totalScore > orderedTotalScores.get(i)){
                    orderedTotalScores.add(i, totalScore);
                    orderedUsernames.add(i, user.getUsername());
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) { // If not already added it must be the lowest score out of everyone so put at the end
                orderedTotalScores.add(orderedTotalScores.size(), totalScore);
                orderedUsernames.add(orderedUsernames.size(), user.getUsername());
            }

        }
        // Print the first 50 names and total scores (or however many there are on the server)
        for (int i = 0; i < orderedUsernames.size(); i++) {
            if (i >= 50) {
                break;
            }
            printWriter.println(orderedUsernames.get(i));
            printWriter.println(orderedTotalScores.get(i));
        }
        // Print "done" when finished so client know not to expect any more
        printWriter.println("done");
    }

}

