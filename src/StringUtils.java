// StringUtils.java
public class StringUtils {
    public static String removeWhitespace(String string) {
        if (string == null) return "";
        string = string.trim();
        return string.replaceAll("\\s+", " ");
    }

    public static String toCaseConversion(String string, TextCase textCase) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        string = removeWhitespace(string);
        switch (textCase) {
            case ALIKE:
                return string;
            case LOWER:
                return string.toLowerCase();
            case UCFIRST:
                if (string.isEmpty()) return string;
                return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
            case UCWORDS:
                String lower = string.toLowerCase();
                String[] words = lower.split(" ");
                for (int i = 0; i < words.length; i++) {
                    if (!words[i].isEmpty()) {
                        words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
                    }
                }
                return String.join(" ", words);
            case UPPER:
                return string.toUpperCase();
            default:
                return string;
        }
    }
}