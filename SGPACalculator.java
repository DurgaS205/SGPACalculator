import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SGPACalculator {
    static class Subject {
        String name;
        int credit;
        String grade;

        Subject(String name, int credit, String grade) {
            this.name = name;
            this.credit = credit;
            this.grade = grade;
        }
    }

    static double getGradePoint(String grade) {
        switch (grade) {
            case "A+": return 10;
            case "A": return 9;
            case "B+": return 8;
            case "B": return 7;
            case "C+": return 6;
            case "C": return 5;
            case "F": return 0;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        List<Subject> subjectList = new ArrayList<>();

        JFrame frame = new JFrame("Student SGPA Calculator");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(30, 30, 100, 30);
        frame.add(subjectLabel);

        JTextField subjectField = new JTextField();
        subjectField.setBounds(150, 30, 200, 30);
        frame.add(subjectField);

        JLabel creditLabel = new JLabel("Credit:");
        creditLabel.setBounds(30, 80, 100, 30);
        frame.add(creditLabel);

        JTextField creditField = new JTextField();
        creditField.setBounds(150, 80, 200, 30);
        frame.add(creditField);

        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setBounds(30, 130, 100, 30);
        frame.add(gradeLabel);

        String[] grades = {"A+", "A", "B+", "B", "C+", "C", "F"};
        JComboBox<String> gradeBox = new JComboBox<>(grades);
        gradeBox.setBounds(150, 130, 200, 30);
        frame.add(gradeBox);

        JButton addButton = new JButton("Add Subject");
        addButton.setBounds(150, 180, 200, 30);
        frame.add(addButton);

        JTextArea subjectListArea = new JTextArea();
        subjectListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(subjectListArea);
        scrollPane.setBounds(30, 230, 420, 200);
        frame.add(scrollPane);

        JButton calculateButton = new JButton("Calculate SGPA");
        calculateButton.setBounds(150, 450, 200, 30);
        frame.add(calculateButton);

        //  Delete Buttons
        JButton deleteLastButton = new JButton("Delete Last");
        deleteLastButton.setBounds(50, 500, 150, 30);
        frame.add(deleteLastButton);

        JButton deleteAllButton = new JButton("Delete All");
        deleteAllButton.setBounds(250, 500, 150, 30);
        frame.add(deleteAllButton);

        //  Add Subject Logic
        addButton.addActionListener(e -> {
            String subject = subjectField.getText();
            String creditText = creditField.getText();
            String grade = (String) gradeBox.getSelectedItem();

            if (subject.isEmpty() || creditText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter all the fields.");
                return;
            }

            try {
                int credit = Integer.parseInt(creditText);
                subjectList.add(new Subject(subject, credit, grade));
                subjectListArea.append(subject + " | Grade: " + grade + " | Credit: " + credit + "\n");

                subjectField.setText("");
                creditField.setText("");
                gradeBox.setSelectedIndex(0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Credit must be a number.");
            }
        });

        //  Calculate SGPA Logic
        calculateButton.addActionListener(e -> {
            if (subjectList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No subjects added.");
                return;
            }

            double totalCredits = 0;
            double totalGradePoints = 0;

            for (Subject sub : subjectList) {
                int credit = sub.credit;
                double gradePoint = getGradePoint(sub.grade);
                totalCredits += credit;
                totalGradePoints += credit * gradePoint;
            }

            double sgpa = totalGradePoints / totalCredits;
            String result = String.format("Your SGPA is %.2f", sgpa);
            JOptionPane.showMessageDialog(frame, result);
        });

        // Delete Last Subject
        deleteLastButton.addActionListener(ev1 -> {
            if (!subjectList.isEmpty()) {
                subjectList.remove(subjectList.size() - 1);
                subjectListArea.setText("");
                for (Subject sub : subjectList) {
                    subjectListArea.append(sub.name + " | Grade: " + sub.grade + " | Credit: " + sub.credit + "\n");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No subjects to delete.");
            }
        });

        //  Delete All Subjects
        deleteAllButton.addActionListener(ev2 -> {
            if (!subjectList.isEmpty()) {
                subjectList.clear();
                subjectListArea.setText("");
                JOptionPane.showMessageDialog(frame, "All subjects deleted.");
            } else {
                JOptionPane.showMessageDialog(frame, "Nothing to delete.");
            }
        });

        frame.setVisible(true);
    }
}
