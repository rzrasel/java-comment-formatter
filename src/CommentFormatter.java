// CommentFormatter.java
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
        comboBox.setPreferredSize(new Dimension(300, 40));
        // Prevent layout shift by setting prototype for wider items
        comboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
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