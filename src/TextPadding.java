// TextPadding.java
import java.util.Arrays;

public class TextPadding {
    protected String sourceText = "";
    protected int fullLength = 64;
    protected int tempFullLength = 0;
    protected int remainingLength = 0;
    protected String padString = "-";
    protected TextCase textCase = TextCase.UPPER;
    protected PaddingPlace paddingPlace = PaddingPlace.CENTER;
    protected int tabCount = 0;
    protected int tabSpace = 4;
    protected String formattedText = "";

    public TextPadding setFullLength(int length) {
        this.fullLength = length;
        return this;
    }

    public TextPadding setNumberOfTabs(int numOfTabs) {
        this.tabCount = numOfTabs;
        return this;
    }

    public TextPadding setPaddingCharacter(String string) {
        this.padString = string;
        return this;
    }

    public TextPadding setPaddingPlace(PaddingPlace paddingPlace) {
        this.paddingPlace = paddingPlace;
        return this;
    }

    public TextPadding setSourceText(String string) {
        this.sourceText = string.trim();
        return this;
    }

    public TextPadding setTextCase(TextCase textCase) {
        this.textCase = textCase;
        return this;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public void execute() {
        if (sourceText == null || sourceText.trim().isEmpty()) {
            formattedText = "";
            return;
        }
        sourceText = StringUtils.removeWhitespace(sourceText);
        sourceText = StringUtils.toCaseConversion(sourceText, textCase);
        calculatePadding();
    }

    private void calculatePadding() {
        tempFullLength = fullLength - (tabCount * tabSpace);
        int textLen = sourceText.length();
        remainingLength = tempFullLength - textLen - 2; // -2 for outer | |
        if (remainingLength < 0) {
            remainingLength = 0;
        }
        setPadding(paddingPlace);
    }

    private void setPadding(PaddingPlace paddingPlace) {
        String text = sourceText;
        int padsTotal = remainingLength;
        String leftPads = "";
        String rightPads = "";
        if (paddingPlace == PaddingPlace.CENTER) {
            int left = padsTotal / 2;
            int right = padsTotal - left;
            leftPads = repeat(padString, left);
            rightPads = repeat(padString, right);
        } else if (paddingPlace == PaddingPlace.LEFT) {
            // Left padding: pads on left of text
            leftPads = repeat(padString, padsTotal);
        } else if (paddingPlace == PaddingPlace.RIGHT) {
            // Right padding: pads on right of text
            rightPads = repeat(padString, padsTotal);
        }
        formattedText = "|" + leftPads + text + rightPads + "|";
    }

    private String repeat(String pad, int times) {
        if (times <= 0 || pad.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(pad);
        }
        return sb.toString();
    }
}