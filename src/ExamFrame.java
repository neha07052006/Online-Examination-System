
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class ExamFrame extends JFrame {

    String username;

    int currentQuestion = 0;
    int score = 0;

    int[] selectedAnswers = {-1, -1, -1, -1, -1};

    int remainingSeconds = 30 * 60;

    Timer examTimer;
    boolean examSubmitted = false;

    String[] questions = {
        "Which language is used for Android Development?",
        "Which one is a Database?",
        "Which keyword is used to create a class in Java?",
        "Which method is the entry point of Java program?",
        "Which symbol is used to end a statement in Java?"
    };

    String[][] options = {
        {"HTML", "Java", "CSS", "SQL"},
        {"MySQL", "HTML", "CSS", "Java"},
        {"function", "define", "class", "create"},
        {"start()", "main()", "run()", "execute()"},
        {":", ".", ";", ","}
    };

    int[] answers = {1, 0, 2, 1, 2};

    JLabel questionLabel;
    JLabel timerLabel;

    JRadioButton[] optionButtons;
    ButtonGroup group;

    JButton nextButton;
    JButton previousButton;

    public ExamFrame(String username) {

        this.username = username;

        setTitle("Online Examination System - Exam");
        setSize(600, 480);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                int choice = JOptionPane.showConfirmDialog(
                    ExamFrame.this,
                    "Are you sure you want to quit the exam?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    examTimer.stop();
                    dispose();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("ONLINE EXAMINATION");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBounds(150, 20, 300, 35);
        panel.add(title);

        timerLabel = new JLabel("Time Left: 30:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        timerLabel.setBounds(220, 65, 200, 30);
        panel.add(timerLabel);

        questionLabel = new JLabel();
        questionLabel.setBounds(40, 110, 520, 35);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(questionLabel);

        optionButtons = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {

            optionButtons[i] = new JRadioButton();

            optionButtons[i].setBounds(
                60, 160 + (i * 40), 400, 30
            );

            optionButtons[i].setBackground(
                new Color(230, 240, 255)
            );

            group.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        previousButton = new JButton("PREVIOUS");
        previousButton.setBounds(100, 350, 130, 35);
        panel.add(previousButton);

        nextButton = new JButton("NEXT");
        nextButton.setBounds(350, 350, 130, 35);
        panel.add(nextButton);

        previousButton.addActionListener(e -> previousQuestion());
        nextButton.addActionListener(e -> nextQuestion());

        add(panel);

        loadQuestion();

        examTimer = new Timer(1000, e -> {

            remainingSeconds--;

            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            timerLabel.setText(
                String.format("Time Left: %02d:%02d", minutes, seconds)
            );

            if (remainingSeconds <= 0) {

                examTimer.stop();

                JOptionPane.showMessageDialog(
                    this,
                    "Time is over! Your exam will be submitted automatically."
                );

                showResult();
            }
        });

        setVisible(true);
        examTimer.start();
    }

    // Load Question
    private void loadQuestion() {

        questionLabel.setText(
            "Q" + (currentQuestion + 1) + ". " +
            questions[currentQuestion]
        );

        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(
                options[currentQuestion][i]
            );
        }

        int savedAnswer = selectedAnswers[currentQuestion];

        if (savedAnswer != -1) {
            optionButtons[savedAnswer].setSelected(true);
        }

        previousButton.setEnabled(currentQuestion > 0);

        if (currentQuestion == questions.length - 1) {
            nextButton.setText("SUBMIT");
        } else {
            nextButton.setText("NEXT");
        }
    }

    // Save Selected Answer
    private void saveAnswer() {

        for (int i = 0; i < 4; i++) {

            if (optionButtons[i].isSelected()) {
                selectedAnswers[currentQuestion] = i;
                return;
            }
        }

        selectedAnswers[currentQuestion] = -1;
    }

    // Next Question
    private void nextQuestion() {

        saveAnswer();

        if (currentQuestion == questions.length - 1) {

            for (int answer : selectedAnswers) {

                if (answer == -1) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Please answer all questions!"
                    );

                    return;
                }
            }

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit the exam?",
                "Confirm Submit",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                showResult();
            }

            return;
        }

        currentQuestion++;
        loadQuestion();
    }

    // Previous Question
    private void previousQuestion() {

        saveAnswer();

        if (currentQuestion > 0) {
            currentQuestion--;
            loadQuestion();
        }
    }

    // Show Detailed Result
    private void showResult() {

        if (examSubmitted) {
            return;
        }

        saveAnswer();

        examSubmitted = true;
        examTimer.stop();

        score = 0;
        int correct = 0;
        int incorrect = 0;

        StringBuilder details = new StringBuilder();

        for (int i = 0; i < questions.length; i++) {

            details.append("\nQ").append(i + 1).append(": ")
                   .append(questions[i]).append("\n");

            if (selectedAnswers[i] == answers[i]) {

                score++;
                correct++;

                details.append("Your Answer: ")
                       .append(options[i][selectedAnswers[i]])
                       .append("\nStatus: CORRECT\n");

            } else {

                incorrect++;

                details.append("Your Answer: ");

                if (selectedAnswers[i] == -1) {
                    details.append("Not Answered\n");
                } else {
                    details.append(
                        options[i][selectedAnswers[i]]
                    ).append("\n");
                }

                details.append("Correct Answer: ")
                       .append(options[i][answers[i]])
                       .append("\nStatus: INCORRECT\n");
            }
        }

        String status = score >= 3 ? "PASS" : "FAIL";

        int timeTaken = (30 * 60) - remainingSeconds;

        int minutesTaken = timeTaken / 60;
        int secondsTaken = timeTaken % 60;

        String sql = "INSERT INTO results " +
            "(username, score, total_marks, result) " +
            "VALUES (?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection()
        ) {

            if (con == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "Database Connection Failed!"
                );

                examSubmitted = false;
                return;
            }

            try (
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

                ps.setString(1, username);
                ps.setInt(2, score);
                ps.setInt(3, questions.length);
                ps.setString(4, status);

                ps.executeUpdate();
            }

            String resultMessage =
                "ONLINE EXAMINATION RESULT\n\n" +
                "Student: " + username +
                "\nScore: " + score + "/" + questions.length +
                "\nCorrect Answers: " + correct +
                "\nIncorrect Answers: " + incorrect +
                "\nTime Taken: " + minutesTaken + " minutes " +
                secondsTaken + " seconds" +
                "\nFinal Result: " + status +
                "\n\nANSWER BREAKDOWN\n" +
                details;

            JTextArea resultArea = new JTextArea(resultMessage);
            resultArea.setEditable(false);
            resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
            resultArea.setLineWrap(true);
            resultArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(resultArea);
            scrollPane.setPreferredSize(
                new java.awt.Dimension(480, 350)
            );

            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Your Exam Result",
                JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception ex) {

            examSubmitted = false;

            JOptionPane.showMessageDialog(
                this,
                "Error Saving Result: " + ex.getMessage()
            );
        }
    }
}
