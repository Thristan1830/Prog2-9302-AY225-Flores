import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.UUID;

public class AttendanceForm extends JFrame {

    private JTextField nameField;
    private JTextField timeInField;
    private JTextField eSignatureField;
    private JComboBox<String> courseBox;
    private JComboBox<String> yearLevelBox;
    private JComboBox<String> amPmBox;
    private JButton okButton;

    public AttendanceForm() {
        setTitle("Attendance Form");
        setSize(600, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color MAROON = new Color(128, 0, 32);
        Color WHITE = Color.WHITE;

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(MAROON);

        // Name
        panel.add(createLabel("Attendance Name:", WHITE));
        nameField = createTextField(WHITE);
        panel.add(nameField);

        // Course
        panel.add(createLabel("Course:", WHITE));
        courseBox = new JComboBox<>(new String[]{
                "BS Computer Science",
                "BS Information Technology",
                "BS Information Systems",
                "BS Computer Engineering",
                "BS Electrical Engineering",
                "BS Business Administration",
                "BS Accountancy",
                "BS Nursing",
                "BS Psychology",
                "BA Communication"
        });
        styleComboBox(courseBox, WHITE);
        courseBox.setSelectedIndex(-1);
        panel.add(courseBox);

        // Year Level
        panel.add(createLabel("Year Level:", WHITE));
        yearLevelBox = new JComboBox<>(new String[]{
                "1st Year", "2nd Year", "3rd Year", "4th Year"
        });
        styleComboBox(yearLevelBox, WHITE);
        yearLevelBox.setSelectedIndex(-1);
        panel.add(yearLevelBox);

        // Time In
        panel.add(createLabel("Time In:", WHITE));
        JPanel timePanel = new JPanel(new BorderLayout(5, 0));
        timePanel.setBackground(MAROON);

        timeInField = createTextField(WHITE);
        amPmBox = new JComboBox<>(new String[]{"AM", "PM"});
        styleComboBox(amPmBox, WHITE);
        amPmBox.setSelectedIndex(-1);

        timePanel.add(timeInField, BorderLayout.CENTER);
        timePanel.add(amPmBox, BorderLayout.EAST);
        panel.add(timePanel);

        // E-Signature (UUID)
        panel.add(createLabel("E-Signature (Auto-Generated):", WHITE));
        eSignatureField = createTextField(WHITE);
        eSignatureField.setEditable(false);

        String eSignature = UUID.randomUUID().toString();
        eSignatureField.setText(eSignature);

        panel.add(eSignatureField);

        // OK Button
        okButton = new JButton("OK");
        okButton.setEnabled(false);
        okButton.setBackground(WHITE);
        okButton.setForeground(MAROON);
        okButton.setFocusPainted(false);

        panel.add(new JLabel());
        panel.add(okButton);

        // Validation
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { checkFields(); }
            public void removeUpdate(DocumentEvent e) { checkFields(); }
            public void changedUpdate(DocumentEvent e) { checkFields(); }
        };

        nameField.getDocument().addDocumentListener(listener);
        timeInField.getDocument().addDocumentListener(listener);

        courseBox.addActionListener(e -> checkFields());
        yearLevelBox.addActionListener(e -> checkFields());
        amPmBox.addActionListener(e -> checkFields());

        add(panel);
        setVisible(true);
    }

    private JLabel createLabel(String text, Color fg) {
        JLabel label = new JLabel(text);
        label.setForeground(fg);
        return label;
    }

    private JTextField createTextField(Color bg) {
        JTextField field = new JTextField();
        field.setBackground(bg);
        field.setForeground(Color.BLACK);
        return field;
    }

    private void styleComboBox(JComboBox<String> box, Color bg) {
        box.setBackground(bg);
        box.setForeground(Color.BLACK);
    }

    private void checkFields() {
        boolean allFilled =
                !nameField.getText().trim().isEmpty() &&
                courseBox.getSelectedIndex() != -1 &&
                yearLevelBox.getSelectedIndex() != -1 &&
                !timeInField.getText().trim().isEmpty() &&
                amPmBox.getSelectedIndex() != -1;

        okButton.setEnabled(allFilled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceForm::new);
    }
}
