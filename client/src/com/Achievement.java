package src.com;

public class Achievement {

    private String achievementName; // XachievementY, where X is the level for the achievement and Y represents the number of achievement for that level (eg 4 is 4th achievement)
    private String achievementDescription;
    private int experiencePoints;
    private int achievementType; // E.g. level1, general
    private int numberOfTimes;
    private int achievementNumber; // number of achievement for that level (eg 4 is 4th achievement in that level)

    public Achievement (String achievementName, String achievementDescription, int experiencePoints, int achievementType, int numberOfTimes, int achievementNumber) {
        this.achievementName = achievementName;
        this.achievementDescription = achievementDescription;
        this.experiencePoints = experiencePoints;
        this.achievementType = achievementType;
        this.numberOfTimes = numberOfTimes;
        this.achievementNumber = achievementNumber;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public int getAchievementType() {
        return achievementType;
    }

    public int getNumberOfTimes(){
        return numberOfTimes;
    }

    public String getAchievementName() {
        return achievementName;
    }

}
