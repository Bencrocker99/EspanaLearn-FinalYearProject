package src.com;

import java.util.ArrayList;

public class Level {
    private int levelNumber;
    private int endingScore;
    // Lists of topics which create phrases a word is taken from each topic and put together
    private ArrayList<ArrayList<Topic>> possibleStructures = new ArrayList<ArrayList<Topic>>();
    // List of probabilities for each structure
    private ArrayList<Integer> orderedProbabilities = new ArrayList<Integer>();
    // list of possible tenses for each structure
    private ArrayList<ArrayList<String>> possibleTenses = new ArrayList<ArrayList<String>>();
    private int includePluralsChance;



    public Level (int levelNumber, int endingScore, ArrayList<ArrayList<Topic>> possibleStructures, ArrayList<Integer> orderedProbabilities, ArrayList<ArrayList<String>> possibleTenses,
                  int includePluralsChance){
        this.levelNumber = levelNumber;
        this.endingScore = endingScore;
        this.possibleStructures = possibleStructures;
        this.orderedProbabilities = orderedProbabilities;
        this.possibleTenses = possibleTenses;
        this.includePluralsChance = includePluralsChance;
    }

    public int getEndingScore(){
        return endingScore;
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public ArrayList<ArrayList<Topic>> getPossibleStructures() {
        return possibleStructures;
    }

    public ArrayList<Integer> getOrderedProbabilities() {
        return orderedProbabilities;
    }

    public ArrayList<ArrayList<String>> getPossibleTenses() {
        return possibleTenses;
    }

    public int getIncludePluralsChance() {
        return includePluralsChance;
    }
}
