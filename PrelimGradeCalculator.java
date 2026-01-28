import javax.swing.*;
import java.awt.*;

public class PrelimGradeCalculator extends JFrame {

    private JTextField attendanceField;
    private JTextField lab1Field;
    private JTextField lab2Field;
    private JTextField lab3Field;
    private JTextArea resultArea;

    public PrelimGradeCalculator() {
        setTitle("Prelim Grade Computation System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font monoFont = new Font("Consolas", Font.PLAIN, 13);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel header = new JLabel("Prelim Grade Computation", JLabel.CENTER);
        header.setFont(headerFont);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Inputs"));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(createLabel("Attendance Grade (0–100):", labelFont));
        attendanceField = createTextField(labelFont);
        inputPanel.add(attendanceField);

        inputPanel.add(createLabel("Lab Work 1:", labelFont));
        lab1Field = createTextField(labelFont);
        inputPanel.add(lab1Field);

        inputPanel.add(createLabel("Lab Work 2:", labelFont));
        lab2Field = createTextField(labelFont);
        inputPanel.add(lab2Field);

        inputPanel.add(createLabel("Lab Work 3:", labelFont));
        lab3Field = createTextField(labelFont);
        inputPanel.add(lab3Field);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton computeButton = new JButton("Compute Grades");
        computeButton.setFont(labelFont);
        computeButton.setBackground(new Color(33, 150, 243));
        computeButton.setForeground(Color.WHITE);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(labelFont);

        buttonPanel.add(clearButton);
        buttonPanel.add(computeButton);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        // Results
        resultArea = new JTextArea();
        resultArea.setFont(monoFont);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Computation Results"));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(resultScroll, BorderLayout.SOUTH);

        add(mainPanel);

        computeButton.addActionListener(e -> computeGrades());
        clearButton.addActionListener(e -> resultArea.setText(""));
    }

    private void computeGrades() {
        try {
            double attendance = Double.parseDouble(attendanceField.getText());
            double lab1 = Double.parseDouble(lab1Field.getText());
            double lab2 = Double.parseDouble(lab2Field.getText());
            double lab3 = Double.parseDouble(lab3Field.getText());

            if (attendance < 0 || lab1 < 0 || lab2 < 0 || lab3 < 0) {
                throw new NumberFormatException();
            }

            // Lab Work Average
            double labAverage = (lab1 + lab2 + lab3) / 3;

            // Class Standing
            double classStanding =
                    (attendance * 0.40) +
                    (labAverage * 0.60);

            // Required Prelim Exam Scores
            double requiredPassingExam =
                    (75 - (classStanding * 0.30)) / 0.70;

            double requiredExcellentExam =
                    (100 - (classStanding * 0.30)) / 0.70;

            resultArea.setText("");
            resultArea.append("LAB WORK AVERAGE: " + format(labAverage) + "\n");
            resultArea.append("CLASS STANDING: " + format(classStanding) + "\n\n");

            resultArea.append("REQUIRED PRELIM EXAM SCORE\n");
            resultArea.append("• To PASS (75): " + formatResult(requiredPassingExam) + "\n");
            resultArea.append("• To EXCELLENT (100): " + formatResult(requiredExcellentExam) + "\n\n");

            if (requiredPassingExam <= 100) {
                resultArea.append("REMARKS: Passing the prelim is attainable.");
            } else {
                resultArea.append("REMARKS: Passing the prelim is NOT attainable.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values only.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String format(double value) {
        return String.format("%.2f", value);
    }

    private String formatResult(double value) {
        if (value <= 0) {
            return "Already achieved";
        } else if (value > 100) {
            return "Not attainable";
        } else {
            return String.format("%.2f", value);
        }
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        return field;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() ->
                new PrelimGradeCalculator().setVisible(true)
        );
    }
}
