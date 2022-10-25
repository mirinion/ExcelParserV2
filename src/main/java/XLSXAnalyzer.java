
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXAnalyzer {


    public static ArrayList<Quest> createQuestsList(String sourceFile, int themeNumber) throws IOException {
        ArrayList<Quest> quests = new ArrayList<>();
        XSSFSheet sheet = getSheet(sourceFile, "Sheet1");

        //Алгоритм упрощен, работает только если 4 варианта ответа
        int rowCnt0to4 = 0; //Считает строки от 0 до 4 и сбрасывается. 0 - текст вопроса, 1-4 - текст вариантов ответа

        Iterator<Row> rowIter = sheet.rowIterator();
        while (rowIter.hasNext()){
            if(rowCnt0to4 == 5)
                rowCnt0to4 = 0;
            if(analyzeRow(rowIter.next(), quests, rowCnt0to4, themeNumber)){
                rowCnt0to4++;
            }

        }
        return quests;
    }

    private static XSSFSheet getSheet(String sourceFile, String sheetName) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(sourceFile);
        return wb.getSheet(sheetName);
    }

    private static boolean analyzeRow(Row row, ArrayList<Quest> quests, int rowCnt0to4, int themeNumber) {
        Cell cell0 = row.getCell(0);
        //Если строка пустая
        if (cell0.getStringCellValue().isEmpty()) {
            return false;
        }
        Cell cell1 = row.getCell(1);
        if(isQuest(rowCnt0to4)) { //Если в строке записана формулировка вопроса
            Quest quest = new Quest(themeNumber);
            quest.setQuestNum(parseQuestNum(cell0.getStringCellValue()));
            quest.setQuestText(parseQuestText(cell0.getStringCellValue()));
            quest.setCorrectAns(cell1.getStringCellValue());
            quests.add(quest);

        } else { //Если в строке записан вариант ответа
            Quest quest = quests.get(quests.size()-1); //Последний записанный вопрос
            String answerText = parseAnswerText(cell0.getStringCellValue(), quest.getQuestNum());
            String answerLetter = parseAnswerLetter(cell0.getStringCellValue());
            quest.addAnswer(answerText, answerLetter);
        }
        return true;
    }



    private static boolean isQuest(int rowCnt0to4) {
        return rowCnt0to4 == 0;
    }

    private static int parseQuestNum(String questWholeStr){
        try {
            return Integer.parseInt(questWholeStr.trim().split("\\.", 2)[0]);
        } catch (Exception exception){
            System.err.println(exception + " in quest" + questWholeStr);
        }
        return -1;
    }

    private static String parseQuestText(String questWholeStr) {
        try {
            return questWholeStr.split("\\.", 2)[1];
        } catch (Exception exception){
            System.err.println(exception + " in quest" + questWholeStr);
        }
        return "";
    }

    private static String parseAnswerText(String ansWholeStr, int questNum) {
        try {
            return ansWholeStr.trim().split( "\\)", 2)[1];
        } catch (Exception exception){
            System.err.println(exception + " in ans" + ansWholeStr + "\n" + "questNum " + questNum);
        }
        return "";

    }

    private static String parseAnswerLetter(String ansWholeStr) {
        return ansWholeStr.trim().split( "\\)",2)[0];
    }

}
