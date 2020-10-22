package depends.extractor;

public class LocCalculator {

    public static int calcLoc(String codeLine) {
        codeLine = codeLine.replaceAll("//[\\s\\S]*?\\n", "");
        codeLine = codeLine.replaceAll("/\\*{1,2}[\\s\\S]*?\\*/", "");
        codeLine = codeLine.replaceAll("((\r\n)|\n)[\\s\t]*(\\1)*", "\n");
        codeLine = codeLine.replaceAll("^\\n", "");
        String validCodeLine = codeLine.replaceAll("\n","");
        return codeLine.length()-validCodeLine.length();
    }

}
