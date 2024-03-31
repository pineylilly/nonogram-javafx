package gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.GameSystem;

public class GamePane extends GridPane {

    private int grid_size;
    private static final int GAME_SIZE = 600;
    public GamePane() {
        super();
        this.setPrefWidth(600);
        this.setPrefHeight(600);
        setGrid_size(GameSystem.getInstance().getMap_size());
        initCellPane();
    }

    public int getGrid_size() {
        return grid_size;
    }

    public void setGrid_size(int grid_size) {
        this.grid_size = grid_size;
    }

    private void initCellPane(){
        for(int i = 0; i < grid_size; ++i){
            for(int j =0; j < grid_size; ++j){
                this.add(new CellPane(GAME_SIZE / (grid_size * 1.0),GAME_SIZE / (grid_size * 1.0),i,j),j,i);
            }
        }
    }
}
