package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameSystem;

public class MapSearchPane extends VBox {
    private static MapSearchPane instance;
    private Button resetButton;
    private Button randomButton;
    private ComboBox<String> mapSelector;
    private String mapPath = "";

    public MapSearchPane(){
        // TODO: Initialize MapSearchPane's constructor
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        setupResetButton();
        setupMapSelector();
        setupRandomButton();

        this.getChildren().addAll(resetButton, mapSelector, randomButton);
    }

    private void setupResetButton(){
        Button btn = new Button("Reset");
        btn.setFont(Font.font("Tohama", FontWeight.BOLD,16));
        btn.setPrefWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
        btn.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        btn.setOnMouseEntered(e -> btn.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null))));
        btn.setOnMouseExited(e -> btn.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null))));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameSystem.getInstance().newGame(mapPath);
            }
        });
        this.resetButton = btn;
    }

    private void setupRandomButton(){
        Button btn = new Button("Random Map");
        btn.setFont(Font.font("Tohama", FontWeight.BOLD,16));
        btn.setPrefWidth(150);
        btn.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
        btn.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        btn.setOnMouseEntered(e -> btn.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null))));
        btn.setOnMouseExited(e -> btn.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null))));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameSystem.getInstance().newGame();
            }
        });
        this.randomButton = btn;
    }

    private void setupMapSelector() {
        mapSelector = new ComboBox<String>();
        mapSelector.getItems().addAll(GameSystem.getInstance().getMapPaths());
        mapSelector.setPrefWidth(150);
        mapSelector.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
        mapSelector.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(1))));
        mapSelector.setOnMouseEntered(e -> mapSelector.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null))));
        mapSelector.setOnMouseExited(e -> mapSelector.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null))));
        mapSelector.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mapPath = mapSelector.getValue();
                GameSystem.getInstance().newGame(mapPath);
            }
        });
    }

    public static MapSearchPane getInstance() {
        if (instance == null){
            instance = new MapSearchPane();
        }
        return instance;
    }

    public void setMapSelectorValue(String name) {
        mapSelector.setValue(name);
    }
}
