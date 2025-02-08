import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class QuizApplication {

    private JFrame frame;
    private JLabel questionLabel, timerLabel, scoreLabel;
    private JButton[] optionButtons;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeLeft = 10; // 10 seconds per question

    private String[][] questions = {
            {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "0"},
            {"Which is the largest planet?", "Earth", "Mars", "Jupiter", "Venus", "2"},
            {"Who wrote 'Hamlet'?", "Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen", "1"}
    };

    public QuizApplication() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel("", JLabel.CENTER);
        frame.add(questionLabel);

        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new OptionButtonListener(i));
            frame.add(optionButtons[i]);
        }

        timerLabel = new JLabel("Time left: 10", JLabel.CENTER);
        frame.add(timerLabel);

        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        frame.add(scoreLabel);

        loadQuestion();
        frame.setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex][0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questions[currentQuestionIndex][i + 1]);
            }
            timeLeft = 10;
            timerLabel.setText("Time left: " + timeLeft);
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    currentQuestionIndex++;
                    loadQuestion();
                }
            }
        });
        timer.start();
    }

    private void showResult() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(2, 1));
        scoreLabel.setText("Final Score: " + score);
        frame.add(scoreLabel);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartQuiz());
        frame.add(restartButton);

        frame.revalidate();
        frame.repaint();
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(6, 1));
        frame.add(questionLabel);
        for (JButton button : optionButtons) {
            frame.add(button);
        }
        frame.add(timerLabel);
        frame.add(scoreLabel);
        loadQuestion();
    }

    private class OptionButtonListener implements ActionListener {
        private int optionIndex;

        public OptionButtonListener(int optionIndex) {
            this.optionIndex = optionIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            if (optionIndex == Integer.parseInt(questions[currentQuestionIndex][5])) {
                score++;
                scoreLabel.setText("Score: " + score);
            }
            currentQuestionIndex++;
            loadQuestion();
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}
