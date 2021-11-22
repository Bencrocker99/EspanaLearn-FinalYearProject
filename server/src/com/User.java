package com;

import src.com.Achievement;

import java.util.HashMap;

public class User {
    private String username;
    private String encryptedPassword;
    private int highestScore;
    private int highestLevel;
    private int experiencePoints;
    private int totalScore;
    private double soundEffectsVolume;
    private HashMap<String, Integer> achievementsProgress = new HashMap<String, Integer>();

    public User(String username, String password) {
        this.username = username;
        this.encryptedPassword = password;
        soundEffectsVolume = 0.5;
        highestScore = 0;
        highestLevel = 1;
        totalScore = 0;
        experiencePoints = 0;
    }


    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public double getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public void setSoundEffectsVolume(double volume) {
        soundEffectsVolume = volume;
    }

    public void incrementExperiencePoints(int experiencePoints) {
        this.experiencePoints = this.experiencePoints + experiencePoints;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public HashMap<String, Integer> getAchievementsProgress() {
        return achievementsProgress;
    }

    public void addAchievement(Achievement achievement) {
        achievementsProgress.put(achievement.getAchievementName(), 0);
    }


}