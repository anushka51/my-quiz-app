package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Question {
    String question;
    String[] options;
    int correctAnswerIndex;

    Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class Login extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton startButton, nextButton, backButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft = 15; // Time limit in seconds
    private JLabel rulesLabel;
    private JLabel questionLabel; // Label to display the current question

    public Login() {
        setLayout(null);
        initializeQuizQuestions();

        // Load the quiz image
        ImageIcon quizIcon = new ImageIcon("C:\\Users\\admin\\Downloads\\icon image\\quiz.png");
        if (quizIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Error loading quiz image.");
            return; // Exit if there's an issue
        }

        // Scale the quiz image
        Image quizImage = quizIcon.getImage().getScaledInstance(550, 500, Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(quizImage));
        imageLabel.setBounds(650, 0, 350, 500);
        add(imageLabel);

        // Add heading label
        JLabel heading = new JLabel("Quiz Test");
        heading.setBounds(200, 30, 300, 45);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setForeground(new Color(22, 99, 54));
        add(heading);

        // Create rules JLabel with HTML
        rulesLabel = new JLabel("<html>" +
                "<h2>Quiz Instructions</h2>" +
                "<p style='font-size:16px;'>Do not cheat.<br>" +
                "Read each question carefully.<br>" +
                "Manage your time wisely.<br>" +
                "Participants in the quiz are open to all persons above 18 years old.<br>" +
                "There are a total of 10 questions.<br>" +
                "You have only 15 seconds to answer each question.<br>" +
                "No cell phones or other secondary devices are allowed.<br>" +
                "No talking.<br>" +
                "</p>" +
                "</html>");
        rulesLabel.setBounds(50, 100, 600, 200);
        rulesLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rulesLabel.setForeground(new Color(22, 99, 54));
        rulesLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
        add(rulesLabel);

        // Add name label and text field
        JLabel nameLabel = new JLabel("Enter your Name");
        nameLabel.setBounds(150, 410, 300, 20);
        nameLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
        nameLabel.setForeground(new Color(22, 99, 54));
        add(nameLabel);

        textField = new JTextField();
        textField.setBounds(50, 440, 400, 25);
        textField.setFont(new Font("Times New Roman", Font.BOLD, 18));
        add(textField);

        // Create Start button
        startButton = new JButton("Start Quiz");
        startButton.setBounds(50, 480, 130, 30);
        startButton.setBackground(new Color(22, 99, 54));
        startButton.setForeground(Color.PINK);
        startButton.addActionListener(this);
        add(startButton);

        // Create Next button
        nextButton = new JButton("Next");
        nextButton.setBounds(200, 480, 130, 30);
        nextButton.setBackground(new Color(22, 99, 54));
        nextButton.setForeground(Color.PINK);
        nextButton.addActionListener(this);
        nextButton.setEnabled(false); // Disable until the quiz starts
        add(nextButton);

        // Create Back button
        backButton = new JButton("Back");
        backButton.setBounds(350, 480, 130, 30);
        backButton.setBackground(new Color(22, 99, 54));
        backButton.setForeground(Color.PINK);
        backButton.addActionListener(this);
        add(backButton);

        // Options for the question
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionButtons[i].setBounds(50, 150 + (i * 35), 600, 30);
            optionButtons[i].setFont(new Font("Tahoma", Font.PLAIN, 16));
            add(optionButtons[i]);
        }

        // Timer label
        timerLabel = new JLabel("Time left: " + timeLeft);
        timerLabel.setBounds(50, 310, 200, 30); // Adjusted position for spacing
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(timerLabel);

        // Frame settings
        setSize(1000, 600);
        setLocation(200, 150);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeQuizQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What does JVM stand for?", new String[]{"Java Variable Manager", "Java Virtual Machine", "Java Version Manager", "Java Verified Machine"}, 1));
        questions.add(new Question("Which of these is a valid declaration of a boolean?", new String[]{"boolean b = 1;", "boolean b = 'true';", "boolean b = false;", "boolean b = 0;"}, 2));
        questions.add(new Question("Which of these is not a Java feature?", new String[]{"Dynamic", "Architecture Neutral", "Use of pointers", "Object-oriented"}, 2));
        questions.add(new Question("What is the default value of a boolean variable?", new String[]{"null", "0", "false", "true"}, 2));
        questions.add(new Question("Which method can be used to find the length of a string?", new String[]{"length()", "getLength()", "size()", "strlen()"}, 0));
        questions.add(new Question("Which of these keywords is used to define a variable in Java?", new String[]{"var", "let", "define", "int"}, 3));
        questions.add(new Question("What is the size of a char in Java?", new String[]{"1 byte", "2 bytes", "4 bytes", "8 bytes"}, 1));
        questions.add(new Question("Which of these is a valid main method declaration in Java?", new String[]{"public static void main(String args[])", "void main(String args)", "public void main(String[] args)", "public static main(String args[]) "}, 0));
        questions.add(new Question("Which package contains the Random class?", new String[]{"java.util package", "java.lang package", "java.awt package", "java.io package"}, 0));
        questions.add(new Question("Which exception is thrown when a divide by zero occurs?", new String[]{"ArithmeticException", "NullPointerException", "IndexOutOfBoundsException", "IOException"}, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startQuiz();
        } else if (e.getSource() == nextButton) {
            checkAnswer();
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                showQuestion();
            } else {
                JOptionPane.showMessageDialog(this, "Quiz completed! Your score: " + score);
                dispose(); // Close the application after completion
            }
        } else if (e.getSource() == backButton) {
            dispose(); // Close the application
        }
    }

    private void startQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        startButton.setEnabled(false); // Disable the start button
        nextButton.setEnabled(true); // Enable the next button
        rulesLabel.setVisible(false); // Hide instructions
        showQuestion();
    }

    private void showQuestion() {
        // Remove previous question label if it exists
        if (questionLabel != null) {
            remove(questionLabel);
        }

        Question question = questions.get(currentQuestionIndex);

        // Create or update a label to display the question
        questionLabel = new JLabel(question.question);
        questionLabel.setBounds(50, 60, 600, 30); // Adjust position and size as needed
        questionLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(questionLabel); // Add the label to the JFrame

        // Clear previous option texts and unselect radio buttons
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(question.options[i]);
            optionButtons[i].setSelected(false);
        }

        // Reset timer and update timer label
        timerLabel.setText("Time left: 15");
        timeLeft = 15;

        // Start the timer
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(Login.this, "Time's up! The correct answer was: " + question.options[question.correctAnswerIndex]);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        showQuestion();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Quiz completed! Your score: " + score);
                        dispose();
                    }
                }
            }
        });
        timer.start();

        // Refresh the JFrame to reflect changes
        revalidate();
        repaint();
    }

    private void checkAnswer() {
        int selectedOptionIndex = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOptionIndex = i;
                break;
            }
        }

        Question question = questions.get(currentQuestionIndex);
        if (selectedOptionIndex == question.correctAnswerIndex) {
            score++;
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! Correct answer is: " + question.options[question.correctAnswerIndex]);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
