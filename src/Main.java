import javax.swing.*;
import java.awt.*;

//|---------------MAIN CLASS FOR COMMENT FORMATTER---------------|
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CommentFormatter cf = new CommentFormatter();
            cf.setVisible(true);
        });
    }
}