package src.com;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private String topicName;
    private List<Word> topicWords = new ArrayList<>();


    public List<Word> getTopicWords() {
        return topicWords;
    }

    public Topic(String name) {
        topicName = name;
    }

    public void addWord(Word word) {
        topicWords.add(word);
    }

    public String getTopicName() {
        return this.topicName;
    }

}
