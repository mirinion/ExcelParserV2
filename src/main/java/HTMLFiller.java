import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class HTMLFiller {
    static private final String CORRECT_TAG = "=";
    static private final String INCORRECT_TAG="~";

    public static void fill(String target, ArrayList<Quest> quests) throws IOException {
        try (FileWriter fw = new FileWriter(target, false)) {

            for (Quest quest : quests) {
                StringBuilder sb = new StringBuilder();
                String header1 = String.format("// question: %d name:Тема%d Вопрос_%d\n", quest.getQuestNum(), quest.getThemeNum(), quest.getQuestNum());
                String header2 = String.format("::Тема%d Вопрос_%d::[html]<p>\n", quest.getThemeNum(), quest.getQuestNum());

                sb.append(header1).append(header2).append(quest.getQuestText()).append("\n");
                sb.append("</p>");
                for (Quest.Answer ans : quest.getAnswers()) {

                    if (ans.getAnswerLetter().charAt(0) == 'А') {
                        sb.append("{");
                    }
                    if (ans.getAnswerLetter().equals(quest.getCorrectAns())) {
                        sb.append(CORRECT_TAG);
                    } else {
                        sb.append(INCORRECT_TAG);
                    }
                    sb.append("<p>");
                    sb.append("\n").append(ans.getAnswerText()).append("\n");
                    sb.append("</p>");
                }
                sb.append("}\n\n");

                fw.append(sb);
                fw.flush();
            }
        }
    }
}
