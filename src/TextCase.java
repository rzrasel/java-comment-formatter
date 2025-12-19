// TextCase.java
public enum TextCase {
    ALIKE("Alike Case"),
    LOWER("Lower Case"),
    UCFIRST("UcFirst Case"),
    UCWORDS("UcWords Case"),
    UPPER("Upper Case");

    private final String value;

    TextCase(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}