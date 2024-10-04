package dev.nkkrisz.coffeetype;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    @FXML
    private Label testText;

    @FXML
    private Slider difficultySlider;

    @FXML
    private TextField userInput;

    private String test;
    private int nextChar = 0;
    private int mistakes = 0;
    private long startTime = 0;  // Variable to store the start time in milliseconds

    @FXML
    private void loadTest() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("tests.txt")))) {
            for (int i = 0; i < (int) difficultySlider.getValue() - 1; i++) {
                reader.readLine();
            }
            test = reader.readLine();
            testText.setText(test);

            userInput.clear();
            userInput.setEditable(true);
            nextChar = 0;
            mistakes = 0;

        } catch (IOException e) {
            testText.setText(String.valueOf(e));
        }
    }

    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }

    @FXML
    private void initialize() {
        loadTest();

        userInput.setOnKeyTyped(event -> {
            if (nextChar == 1) {
                startTime = System.currentTimeMillis();
            }

            char typedChar = event.getCharacter().charAt(0); // Get the typed character

            // Check if the typed character matches the current character in the test string
            if (Character.toString(typedChar).matches("[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ.,;:?!'\"()\\-\\s]")
                    && typedChar == test.charAt(nextChar)) {
                nextChar++;
                testText.setText(test.substring(nextChar, test.length()));

                if (nextChar == test.length()) {  // Test is completed
                    long endTime = System.currentTimeMillis();
                    double timeTaken = (endTime - startTime) / 60000.0; // Convert milliseconds to minutes

                    int wordCount = countWords(test); // Count words in the test
                    double wpm = wordCount / timeTaken; // Calculate WPM

                    testText.setText("Test completed with " + mistakes + " mistakes. WPM: " + String.format("%.2f", wpm));
                    userInput.setEditable(false);
                }
            } else {
                mistakes++;
            }
        });
    }
}
