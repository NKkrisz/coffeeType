package dev.nkkrisz.coffeetype;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private long startTime = 0;

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

    private void showMistakeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Test Stopped");
        alert.setHeaderText(null);
        alert.setContentText("You have made 3 mistakes. The test is now stopped.");
        alert.showAndWait();
        testText.setText("Choose A Difficulty And Load The Test!");
        userInput.clear();
    }

    @FXML
    private void initialize() {
        loadTest();

        userInput.setOnKeyTyped(event -> {
            String typedText = event.getCharacter();

            if (typedText.equals("\b")) {
                return;
            }

            if (nextChar == 0) {
                startTime = System.currentTimeMillis();
            }

            if (nextChar < test.length() && typedText.charAt(0) == test.charAt(nextChar)) {
                nextChar++;
                testText.setText(test.substring(nextChar));

                if (nextChar == test.length()) {
                    long endTime = System.currentTimeMillis();
                    double timeTaken = (endTime - startTime) / 60000.0;
                    int wordCount = countWords(test);
                    double wpm = wordCount / timeTaken;
                    testText.setText("Test completed with " + mistakes + " mistake(s). WPM: " + String.format("%.2f", wpm));
                    userInput.setEditable(false);
                }
            } else {
                mistakes++;
                if (mistakes == 3) {
                    showMistakeAlert();
                    userInput.setEditable(false);
                }
            }
        });
    }
}