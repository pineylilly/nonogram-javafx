package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameSystem;

public class ControlPane extends VBox {

    private static ControlPane instance;

    private Text finishText;
    private Text stepText;
    private Text mapNameText;

    private ControlPane() {
        super();
        this.setPrefWidth(400);
        this.setPrefHeight(600);
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(10, 0, 10, 0));
        setupFinishMessage();
        setupMapNameMessage();
        setupStepMessage();

        this.getChildren().addAll(finishText, stepText, mapNameText, MapSearchPane.getInstance());
    }

    private void setupFinishMessage(){
        Text text = new Text("Congratulation! The map is finished!");
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        text.setFill(Color.RED);
        text.setVisible(false);
        this.finishText = text;
    }

    private void setupStepMessage(){
        Text text = new Text("You used 0 step(s)");
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        this.stepText = text;
    }

    private void setupMapNameMessage(){
        Text text = new Text("Please select a map");
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        this.mapNameText = text;
    }

    public void step(){
        stepText.setText("You used " + GameSystem.getInstance().getStep() + " step(s)");
    }

    public void resetText(){
        finishText.setVisible(false);
        stepText.setText("You used 0 step(s)");
        mapNameText.setText("Current Map: " + GameSystem.getInstance().getMapName());
    }

    public void onComplete(){
        finishText.setVisible(true);
    }

    public void onFailed(){
        finishText.setVisible(false);
        stepText.setText("You used 0 step(s)");
        mapNameText.setText("Please select a map");
    }

    public static ControlPane getInstance() {
        if (instance == null){
            instance = new ControlPane();
        }
        return instance;
    }
}
