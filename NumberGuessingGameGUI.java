package oasis;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private static final int MAX_ATTEMPTS = 5; // Maximum number of attempts allowed
    private int randomNumber;
    private int attempts;

    private JTextField rangeField;
    private JTextField guessField;
    private JLabel resultLabel;

    public NumberGuessingGameGUI() {
        super("Number Guessing Game");
        attempts = 0;
        randomNumber = generateRandomNumber(100); // Default range from 1 to 100

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel rangeLabel = new JLabel("Range of Numbers (1 to N): ");
        rangeField = new JTextField("100", 10);
        rangeField.setEditable(false); // Make the range field non-editable

        JLabel guessLabel = new JLabel("Enter your guess: ");
        guessField = new JTextField(10);
        guessField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });

        resultLabel = new JLabel("");

        centerPanel.add(rangeLabel);
        centerPanel.add(rangeField);
        centerPanel.add(guessLabel);
        centerPanel.add(guessField);
        centerPanel.add(guessButton);
        centerPanel.add(resultLabel);

        add(centerPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void playGame() {
        int numberRange;
        try {
            numberRange = Integer.parseInt(rangeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid range. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int userGuess;
        try {
            userGuess = Integer.parseInt(guessField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid guess. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        attempts++;

        if (userGuess == randomNumber) {
            JOptionPane.showMessageDialog(this, "Congratulations! You guessed the correct number: " + randomNumber + " \u2764\ufe0f", "Congratulations!", JOptionPane.PLAIN_MESSAGE);
            guessField.setText(""); // Clear the guess field
            guessField.requestFocus(); // Set focus on the guess field for the next game
            attempts = 0;
            randomNumber = generateRandomNumber(numberRange); // Generate a new random number for the next game
        } else {
            if (attempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this, "Sorry, you exceeded the maximum attempts. The correct number was: " + randomNumber, "Game Over", JOptionPane.PLAIN_MESSAGE);
                guessField.setText(""); // Clear the guess field
                guessField.requestFocus(); // Set focus on the guess field for the next game
                attempts = 0;
                randomNumber = generateRandomNumber(numberRange); // Generate a new random number for the next game
            } else {
                if (userGuess > randomNumber) {
                    resultLabel.setText("You put a higher value. Attempts: " + attempts);
                } else {
                    resultLabel.setText("You put a lower value. Attempts: " + attempts);
                }
            }
        }
    }

    private int generateRandomNumber(int numberRange) {
        Random random = new Random();
        return random.nextInt(numberRange) + 1; // Generate a random number from 1 to the specified range
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGameGUI().setVisible(true);
            }
        });
    }
}
