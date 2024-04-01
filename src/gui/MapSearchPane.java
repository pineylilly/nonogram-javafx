package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameSystem;

public class MapSearchPane extends VBox {
    private Text selectedMap;
    private ComboBox<String> mapSelector;

    private String mapPath;
    private static MapSearchPane instance;
    public MapSearchPane(){
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        mapPath = "";
        addSelectedMapText();
        addConfirmBtn();
        addMapSelector();

    }

    private void addSelectedMapText(){
        Text text = new Text("Selected map is " + getMapName());
        text.setFont(Font.font("Tohama", FontWeight.BOLD,16));
        text.setFill(Color.RED);
        selectedMap = text;
        this.getChildren().add(text);
    }
    private void addConfirmBtn(){
        Button btn = new Button("Load map");
        btn.setFont(Font.font("Tohama", FontWeight.BOLD,16));
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameSystem.getInstance().newGame(mapPath);
            }
        });
        this.getChildren().add(btn);
    }

    private void addMapSelector(){
        mapSelector = new ComboBox<String>();
        mapSelector.getItems().addAll(GameSystem.getInstance().getMap_paths());
        mapSelector.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mapPath = mapSelector.getValue();
                selectedMap.setText("Selected map is " + getMapName());
            }
        });
        this.getChildren().add(mapSelector);
    }
    private String getMapName(){
        if (mapPath == null || mapPath.isBlank()) return "No map is loaded";
        String[] map_finder =  mapPath.strip().split("/");
        return map_finder[map_finder.length - 1].replaceFirst("\\..*","");
    }

    public static MapSearchPane getInstance() {
        if (instance == null){
            instance = new MapSearchPane();
        }
        return instance;
    }
}
