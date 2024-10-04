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

    @FXML
    private void initialize() {
        loadTest();

        userInput.setOnKeyTyped(event -> {
            char typedChar = event.getCharacter().charAt(0); // Get the typed character

            // Updated regex to include letters, accented letters, and punctuation characters
            if (Character.toString(typedChar).matches("[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ.,;:?!'\"()\\-\\s]")
                    && typedChar == test.charAt(nextChar)) {
                nextChar++;
                testText.setText(test.substring(nextChar, test.length()));
                if (nextChar < test.length()) {
                    System.out.println("next char: " + test.charAt(nextChar));
                } else {
                    testText.setText("Test completed with " + mistakes + " mistakes.");
                    userInput.setEditable(false);
                }
            } else {
                mistakes++;
                System.out.println(mistakes);
                System.out.println("next char: " + test.charAt(nextChar));
            }
        });
    }
}
