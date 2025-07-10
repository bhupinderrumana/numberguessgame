import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(fileName)).getImage();
        } catch (Exception e) {
            System.out.println("Background image not found!");
        }
        setLayout(new GridLayout(8, 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class NumberGuessingGame extends JFrame implements ActionListener {

    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;
    private int timeLeft = 30;
    private boolean isWon = false;

    private JTextField guessField;
    private JLabel messageLabel, attemptsLabel, scoreLabel, timerLabel;
    private JButton guessButton, restartButton;
    JButton exitButton = new JButton("Cancel");
    private Timer gameTimer;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel panel = new BackgroundPanel("/download.jpg");
        add(panel);

        JLabel titleLabel = new JLabel("Guess a number from 1 and 100", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        guessField = new JTextField();
        panel.add(guessField);
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("Arial", Font.BOLD, 16));


        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        guessButton.setPreferredSize(new Dimension(5, 3));
        guessButton.setBackground(new Color(139, 104, 208)); // Steel blue
        guessButton.setForeground(Color.BLACK);
        guessButton.addActionListener(this);
        panel.add(guessButton);

        messageLabel = new JLabel("Enter your guess above", JLabel.CENTER);
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        panel.add(messageLabel);

        attemptsLabel = new JLabel("Attempts left: " + (maxAttempts - attempts), JLabel.CENTER);
        attemptsLabel.setForeground(Color.CYAN);
        attemptsLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        panel.add(attemptsLabel);

        timerLabel = new JLabel("Time left: " + timeLeft + " sec", JLabel.CENTER);
        timerLabel.setForeground(Color.GREEN);
        timerLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        panel.add(timerLabel);

        scoreLabel = new JLabel("", JLabel.CENTER);
        scoreLabel.setForeground(Color.GREEN);
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        panel.add(scoreLabel);

        restartButton = new JButton("Play Again");
        restartButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        restartButton.setPreferredSize(new Dimension(5, 3));
        restartButton.setBackground(new Color(89, 228, 39));
        restartButton.addActionListener(e -> resetGame());
        restartButton.setVisible(false);
        panel.add(restartButton);


        setVisible(true);
        startTimer();
        generateRandomNumber();
    }

    private void generateRandomNumber() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attempts = 0;
        isWon = false;
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + " sec");

            if (timeLeft <= 0) {
                gameTimer.stop();
                if (!isWon) {
                    messageLabel.setText("⏰ Time's up! Number was " + randomNumber);
                    scoreLabel.setText("Your score: 0");
                    guessButton.setEnabled(false);
                    restartButton.setVisible(true);
                }
            }
        });
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String guessText = guessField.getText();
        try {
            int guess = Integer.parseInt(guessText);
            attempts++;

            if (guess < 1 || guess > 100) {
                messageLabel.setText("Enter a number between 1 and 100!");
            } else if (guess < randomNumber) {
                messageLabel.setText("Too low! Try again.");
            } else if (guess > randomNumber) {
                messageLabel.setText("Too high! Try again.");
            } else {
                isWon = true;
                gameTimer.stop();
                int score = (100 - (attempts - 1) * 10);
                if (timeLeft > 0) {
                    score += timeLeft; // Bonus
                }
                messageLabel.setText("🎉 Correct! You won!");
                scoreLabel.setText("Your score: " + score);
                guessButton.setEnabled(false);
                restartButton.setVisible(true);
            }

            if (attempts >= maxAttempts && !isWon) {
                gameTimer.stop();
                messageLabel.setText("❌ Out of attempts! Number was " + randomNumber);
                scoreLabel.setText("Your score: 0");
                guessButton.setEnabled(false);
                restartButton.setVisible(true);
            }

            attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
            guessField.setText("");

        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number!");
        }
    }

    private void resetGame() {
        generateRandomNumber();
        guessField.setText("");
        messageLabel.setText("Enter your guess above");
        scoreLabel.setText("");
        timerLabel.setText("Time left: 30 sec");
        attemptsLabel.setText("Attempts left: 10");
        guessButton.setEnabled(true);
        restartButton.setVisible(false);
        timeLeft = 30;
        startTimer();
    }

    public static void main(String[] args) {

        new NumberGuessingGame();
    }
}
