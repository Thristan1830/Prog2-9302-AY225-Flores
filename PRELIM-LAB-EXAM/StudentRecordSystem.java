import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class StudentRecordSystem extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField studentIdField, firstNameField, lastNameField;
    private JTextField lab1Field, lab2Field, lab3Field, prelimField, attendanceField;
    private JButton addButton, deleteButton;
    private final String CSV_FILE = System.getProperty("user.dir") + "/MOCK_DATA.csv";

    public StudentRecordSystem() {
        setTitle("Student Record System");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom close
        setLocationRelativeTo(null);

        // Table setup
        String[] columns = {"StudentID", "First Name", "Last Name",
                "LAB WORK 1", "LAB WORK 2", "LAB WORK 3",
                "PRELIM EXAM", "ATTENDANCE GRADE"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Input fields
        studentIdField = new JTextField(5);
        firstNameField = new JTextField(7);
        lastNameField = new JTextField(7);
        lab1Field = new JTextField(5);
        lab2Field = new JTextField(5);
        lab3Field = new JTextField(5);
        prelimField = new JTextField(5);
        attendanceField = new JTextField(5);

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");

        // Layout
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.add(new JLabel("StudentID:")); inputPanel.add(studentIdField);
        inputPanel.add(new JLabel("First Name:")); inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:")); inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Lab1:")); inputPanel.add(lab1Field);
        inputPanel.add(new JLabel("Lab2:")); inputPanel.add(lab2Field);
        inputPanel.add(new JLabel("Lab3:")); inputPanel.add(lab3Field);
        inputPanel.add(new JLabel("Prelim:")); inputPanel.add(prelimField);
        inputPanel.add(new JLabel("Attendance:")); inputPanel.add(attendanceField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Load CSV data
        loadCSV();

        // Button actions
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        // Save all fields on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveAllInputs(); // save any typed data
                dispose();
                System.exit(0);
            }
        });
    }

    private void loadCSV() {
        try (Scanner scanner = new Scanner(new File(CSV_FILE))) {
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (firstLine) { firstLine = false; continue; } // skip header
                String[] data = line.split(",");
                if (data.length >= 8) {
                    // Trim values to avoid extra spaces
                    for (int i = 0; i < 8; i++) {
                        data[i] = data[i].trim();
                    }
                    tableModel.addRow(new Object[]{
                            data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]
                    });
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "CSV file not found: " + CSV_FILE,
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveCSV() {
        try (PrintWriter writer = new PrintWriter(new File(CSV_FILE))) {
            // Write header
            writer.println("StudentID,First Name,Last Name,LAB WORK 1,LAB WORK 2,LAB WORK 3,PRELIM EXAM,ATTENDANCE GRADE");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    sb.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) sb.append(",");
                }
                writer.println(sb);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAllInputs() {
        String studentId = studentIdField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String lab1 = lab1Field.getText().trim();
        String lab2 = lab2Field.getText().trim();
        String lab3 = lab3Field.getText().trim();
        String prelim = prelimField.getText().trim();
        String attendance = attendanceField.getText().trim();

        if (!studentId.isEmpty() || !firstName.isEmpty() || !lastName.isEmpty() ||
            !lab1.isEmpty() || !lab2.isEmpty() || !lab3.isEmpty() ||
            !prelim.isEmpty() || !attendance.isEmpty()) {

            tableModel.addRow(new String[]{studentId, firstName, lastName,
                    lab1, lab2, lab3, prelim, attendance});
        }

        saveCSV(); // Save table to CSV
    }

    private void addStudent() {
        String studentId = studentIdField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String lab1 = lab1Field.getText().trim();
        String lab2 = lab2Field.getText().trim();
        String lab3 = lab3Field.getText().trim();
        String prelim = prelimField.getText().trim();
        String attendance = attendanceField.getText().trim();

        if (studentId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                lab1.isEmpty() || lab2.isEmpty() || lab3.isEmpty() ||
                prelim.isEmpty() || attendance.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new String[]{studentId, firstName, lastName,
                lab1, lab2, lab3, prelim, attendance});
        saveCSV();

        studentIdField.setText(""); firstNameField.setText(""); lastNameField.setText("");
        lab1Field.setText(""); lab2Field.setText(""); lab3Field.setText("");
        prelimField.setText(""); attendanceField.setText("");
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveCSV();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRecordSystem frame = new StudentRecordSystem();
            frame.setVisible(true);
        });
    }
}
