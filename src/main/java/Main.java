import java.io.IOException;
import java.util.ArrayList;

/*
И снова здравствуйте
 */
public class Main {
    private static final String SOURCE_XLSX = "src/main/resources/source.xlsx";
    private static final String TARGET_TXT = "src/main/resources/target.txt";
    private static final int THEME_NUM = 1;

    public static void main(String[] args) throws IOException {
        ArrayList<Quest> quests = XLSXAnalyzer.createQuestsList(SOURCE_XLSX, THEME_NUM);
        HTMLFiller.fill(TARGET_TXT, quests);
    }
}
