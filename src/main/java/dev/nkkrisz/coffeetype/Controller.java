package dev.nkkrisz.coffeetype;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    @FXML
    private Label testText;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Button loadTest;

    @FXML
    protected void loadTest() {
        System.out.println((int) difficultySlider.getValue());
        testText.setText("a");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("tests.txt")))){
            for (int i = 0; i < (int) difficultySlider.getValue() - 1; i++) {
                reader.readLine();
            }
            testText.setText(reader.readLine());

        } catch (IOException e){
            System.out.println(e);
        }
    }
}