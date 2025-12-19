/ File: CommentFormatter.java **/

// CommentFormatter.java (Fixed transparent caret issue)
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CommentFormatter extends JFrame {
    private JComboBox<String> totalCharsBox;
    private JComboBox<String> numTabsBox;
    private JComboBox<String> paddingPlaceBox;
    private JComboBox<String> textCaseBox;
    private JTextField paddingCharField;
    private JTextField commentTextField;
    private JTextField formattedField;
    private JButton submitButton;

    // Material Design colors
    private static final Color PRIMARY_COLOR = new Color(0x6200EE); // Purple 500
    private static final Color SECONDARY_COLOR = new Color(0x03DAC6); // Teal 200
    private static final Color BACKGROUND_COLOR = new Color(0xFAFAFA); // Grey 50
    private static final Color SURFACE_COLOR = new Color(0xFFFFFF); // White
    private static final Color ON_PRIMARY_COLOR = Color.WHITE;
    private static final Color ON_SURFACE_COLOR = new Color(0x000000); // Black
    private static final Color OUTLINE_COLOR = new Color(0xE0E0E0); // Grey 300
    private static final Color ON_BACKGROUND_COLOR = new Color(0x757575); // Grey 600 (for placeholders/hints)

    public CommentFormatter() {
        initializeLookAndFeel();
        initComponents();
        setupLayout();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // Customize for Material-like theme
            UIManager.put("nimbusBase", new Color(0xFAFAFA));
            UIManager.put("control", new Color(0xFAFAFA));
        } catch (Exception e) {
            // Fall back to system default
        }
    }

    private void initComponents() {
        setTitle("Comment Formatter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Total Characters ComboBox
        totalCharsBox = new JComboBox<>();
        for (int i = 1; i <= 250; i++) {
            totalCharsBox.addItem(i + " Characters");
        }
        totalCharsBox.setSelectedIndex(63); // Default 64
        styleComboBox(totalCharsBox);

        // Number of Tabs ComboBox
        numTabsBox = new JComboBox<>();
        for (int i = 0; i <= 100; i++) {
            numTabsBox.addItem(i + " Tabs");
        }
        numTabsBox.setSelectedIndex(0); // Default 0
        styleComboBox(numTabsBox);

        // Padding Place ComboBox
        paddingPlaceBox = new JComboBox<>();
        for (PaddingPlace p : PaddingPlace.values()) {
            paddingPlaceBox.addItem(p.getValue());
        }
        paddingPlaceBox.setSelectedIndex(0); // Default CENTER
        styleComboBox(paddingPlaceBox);

        // Text Case ComboBox
        textCaseBox = new JComboBox<>();
        for (TextCase t : TextCase.values()) {
            textCaseBox.addItem(t.getValue());
        }
        textCaseBox.setSelectedIndex(4); // Default UPPER
        styleComboBox(textCaseBox);

        // Padding Character Field
        paddingCharField = new JTextField("-", 30);
        paddingCharField.setToolTipText("Enter padding character (default: -)");
        styleTextField(paddingCharField);

        // Comment Text Field
        commentTextField = new JTextField("", 30);
        commentTextField.setToolTipText("Enter the comment text to format");
        styleTextField(commentTextField);

        // Formatted Text Field (read-only)
        formattedField = new JTextField("", 30);
        formattedField.setEditable(false);
        formattedField.setToolTipText("Formatted output (click to select all)");
        styleReadOnlyTextField(formattedField);
        // Add select all on focus
        formattedField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                formattedField.selectAll();
            }
        });

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formatText();
            }
        });
        // Material styling for button
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(ON_PRIMARY_COLOR);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setContentAreaFilled(true);
        submitButton.setOpaque(true);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(SURFACE_COLOR);
        comboBox.setForeground(ON_SURFACE_COLOR);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OUTLINE_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        comboBox.setPreferredSize(new Dimension(200, 40));
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(SURFACE_COLOR);
        textField.setForeground(ON_SURFACE_COLOR);
        textField.setCaretColor(PRIMARY_COLOR);
        textField.setSelectionColor(new Color(0xE8EAF6)); // Light purple for selection
        textField.setSelectedTextColor(ON_SURFACE_COLOR);

        // Rounded border with outline
        Border outlineBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OUTLINE_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        textField.setBorder(outlineBorder);

        // Focus listener for Material ripple-like effect (color change on focus)
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(outlineBorder);
            }
        });

        textField.setPreferredSize(new Dimension(300, 40));
    }

    private void styleReadOnlyTextField(JTextField textField) {
        textField.setBackground(new Color(0xF5F5F5)); // Light gray for read-only
        textField.setForeground(new Color(0x424242)); // Dark gray text
        textField.setCaretColor(new Color(0, 0, 0, 0)); // Transparent caret
        textField.setSelectionColor(new Color(0xE0E0E0)); // Subtle selection
        textField.setSelectedTextColor(ON_SURFACE_COLOR);

        // Subtle border
        Border subtleBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(OUTLINE_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        textField.setBorder(subtleBorder);

        textField.setPreferredSize(new Dimension(300, 40));
    }

    private void setupLayout() {
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(20, 20));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels use on-surface color
        JLabel totalCharsLabel = new JLabel("Total Characters:");
        styleLabel(totalCharsLabel);
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(totalCharsLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(totalCharsBox, gbc);

        JLabel numTabsLabel = new JLabel("Number Of Tabs:");
        styleLabel(numTabsLabel);
        gbc.gridy = 1; gbc.gridx = 0;
        mainPanel.add(numTabsLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(numTabsBox, gbc);

        JLabel paddingPlaceLabel = new JLabel("Padding Place:");
        styleLabel(paddingPlaceLabel);
        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(paddingPlaceLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(paddingPlaceBox, gbc);

        JLabel textCaseLabel = new JLabel("Text Case:");
        styleLabel(textCaseLabel);
        gbc.gridy = 3; gbc.gridx = 0;
        mainPanel.add(textCaseLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(textCaseBox, gbc);

        JLabel paddingCharLabel = new JLabel("Padding Character:");
        styleLabel(paddingCharLabel);
        gbc.gridy = 4; gbc.gridx = 0;
        mainPanel.add(paddingCharLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(paddingCharField, gbc);

        JLabel commentLabel = new JLabel("Comment Text:");
        styleLabel(commentLabel);
        gbc.gridy = 5; gbc.gridx = 0;
        mainPanel.add(commentLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(commentTextField, gbc);

        JLabel formattedLabel = new JLabel("Formatted Comment Text:");
        styleLabel(formattedLabel);
        gbc.gridy = 6; gbc.gridx = 0;
        mainPanel.add(formattedLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(formattedField, gbc);

        // Submit button centered below
        gbc.gridy = 7; gbc.gridx = 1; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleLabel(JLabel label) {
        label.setForeground(ON_SURFACE_COLOR);
        label.setFont(label.getFont().deriveFont(12f));
    }

    private void formatText() {
        try {
            // Parse total characters
            String tcbStr = (String) totalCharsBox.getSelectedItem();
            int totalChars = Integer.parseInt(tcbStr.split(" ")[0]);

            // Parse tabs
            String tabStr = (String) numTabsBox.getSelectedItem();
            int tabs = Integer.parseInt(tabStr.split(" ")[0]);

            // Parse padding place
            String ppStr = (String) paddingPlaceBox.getSelectedItem();
            PaddingPlace pp = getPaddingPlaceByValue(ppStr);

            // Parse text case
            String tcStr = (String) textCaseBox.getSelectedItem();
            TextCase tc = getTextCaseByValue(tcStr);

            // Padding char
            String padChar = paddingCharField.getText().trim();
            if (padChar.isEmpty()) {
                padChar = "-";
            }

            // Comment text
            String comment = commentTextField.getText().trim();
            if (comment.isEmpty()) {
                formattedField.setText("");
                return;
            }

            // Create and execute formatter
            TextPadding tp = new TextPadding();
            tp.setSourceText(comment)
                    .setFullLength(totalChars)
                    .setNumberOfTabs(tabs)
                    .setPaddingCharacter(padChar)
                    .setTextCase(tc)
                    .setPaddingPlace(pp);
            tp.execute();
            formattedField.setText(tp.getFormattedText());
            formattedField.selectAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error formatting text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private PaddingPlace getPaddingPlaceByValue(String value) {
        for (PaddingPlace p : PaddingPlace.values()) {
            if (p.getValue().equals(value)) {
                return p;
            }
        }
        return PaddingPlace.CENTER;
    }

    private TextCase getTextCaseByValue(String value) {
        for (TextCase t : TextCase.values()) {
            if (t.getValue().equals(value)) {
                return t;
            }
        }
        return TextCase.UPPER;
    }
}

/ File: Main.java **/

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CommentFormatter cf = new CommentFormatter();
            cf.setVisible(true);
        });
    }
}

/ File: PaddingPlace.java **/

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

/ File: StringUtils.java **/

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

/ File: TextCase.java **/

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

/ File: TextPadding.java **/

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

- working nicely
- working nicely don't change code structure/architecture/codebase
- don't change code structure
- flow modern material design
- modern material color scheme, color theme
- click on text case dropdown resized all filed need to fixed it
- provide full codebase