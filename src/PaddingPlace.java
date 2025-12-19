// PaddingPlace.java
public enum PaddingPlace {
    CENTER("Center Padding"),
    LEFT("Left Padding"),
    RIGHT("Right Padding");

    private final String value;

    PaddingPlace(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}