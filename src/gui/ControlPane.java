package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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

    private Text finishMessage;
    private Text stepGame;

    private Text mapNameMessage;
    private ControlPane() {
        super();
        this.setPrefWidth(400);
        this.setPrefHeight(600);
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        addFinishMessage();
        addMapNameMessage();
        addStepMessage();
        addNewGameBtn();

    }


    private void addFinishMessage(){
        Text text = new Text("Map is finished");
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        text.setFill(Color.RED);
        text.setVisible(false);
        finishMessage = text;
        this.getChildren().add(finishMessage);
    }

    private void addStepMessage(){
        Text text = new Text("Current Step is 0");
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        stepGame = text;
        this.getChildren().add(stepGame);
    }

    private void addMapNameMessage(){
        Text text = new Text("Current Map is " + GameSystem.getInstance().getMapName());
        text.setFont(Font.font("Tohama",FontWeight.BOLD,16));
        mapNameMessage = text;
        this.getChildren().add(mapNameMessage);
    }

    public void onComplete(){
        finishMessage.setVisible(true);
    }
    private void addNewGameBtn(){
        Button btn = new Button("New game [Random]");
        btn.setFont(Font.font("Tahoma", FontWeight.BOLD,16));

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                randomGameHandle();
            }
        });
        this.getChildren().add(btn);
    }

    public void step(){
        stepGame.setText("Current Step is " + GameSystem.getInstance().getStep());
    }

    private void randomGameHandle(){
        finishMessage.setVisible(false);
        stepGame.setText("Current Step is 0");
        GameSystem.getInstance().loadMap();
        GamePane.getInstance().newGame();
        mapNameMessage.setText("Current Map is " + GameSystem.getInstance().getMapName());

    }

    public static ControlPane getInstance() {
        if (instance == null){
            instance = new ControlPane();
        }
        return instance;
    }
}
