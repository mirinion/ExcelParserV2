import lombok.Data;

import java.util.ArrayList;

@Data
public class Quest {
    private int questNum;
    private int themeNum;
    private String questText;
    private ArrayList<Answer> answers;
    private String correctAns;

    public Quest(int themeNum) {
        this.themeNum = themeNum;
        answers = new ArrayList<>(4);
    }

    public Quest(int questNum, int themeNum, String questText, String correctAns){
        this.questNum = questNum;
        this.themeNum = themeNum;
        this.questText = questText;
        this.correctAns = correctAns;
        this.answers = new ArrayList<>(4);
    }

    @Data
    public static class Answer {
        private final String answerText;
        private final String answerLetter;

        public Answer(String answerText, String answerLetter) {
            this.answerText = answerText;
            this.answerLetter = answerLetter;
        }
    }

    public void addAnswer(String answerText, String answerLetter){
        Answer answer = new Answer(answerText, answerLetter);
        answers.add(answer);
    }

}
