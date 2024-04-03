package gui;

import javafx.scene.layout.GridPane;
import logic.GameSystem;
import java.util.List;
public class GamePane extends GridPane {

    private static final int GAME_SIZE = 620;
    private static final int RULE_SIZE = 4;
    private int gridSize;
    private double paneSize;

    private static GamePane instance;
    private GamePane() {
        super();
        this.setPrefWidth(GAME_SIZE);
        this.setPrefHeight(GAME_SIZE);

    }

    public static GamePane getInstance() {
        if (instance == null){
            instance = new GamePane();
        }
        return instance;
    }

    public void newGame(){
        this.getChildren().clear();

        this.setPrefWidth(GAME_SIZE);
        this.setPrefHeight(GAME_SIZE);
        setGridSize(GameSystem.getInstance().getMapSize());
        paneSize = GAME_SIZE / ((gridSize + RULE_SIZE) * 1.0);
        initEmptyPane();
        initRulePane();
        initCellPane();
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int grid_size) {
        this.gridSize = grid_size;
    }

    private void initEmptyPane(){
        for(int i = 0; i < RULE_SIZE; ++i){
            for(int j = 0; j < RULE_SIZE; ++j){
                this.add(new EmptyPane(paneSize,paneSize,false),j,i);
            }
        }
    }



    private void initRulePane(){
        List<List<Integer>> my_rule = GameSystem.getInstance().getCurrentRule();
        for (int j = RULE_SIZE; j < (RULE_SIZE + gridSize); j++){
            int last_idx = my_rule.get(j - RULE_SIZE).size() - 1;
            for(int i = RULE_SIZE - 1; i >= 0; i--){
                if (last_idx < 0) {
                    this.add(new EmptyPane(paneSize,paneSize,true),j,i);
                } else {
                    int rule_number = my_rule.get(j-RULE_SIZE).get(last_idx);
                    this.add(new NumberPane(paneSize,paneSize,rule_number),j,i);
                    last_idx--;
                }
            }
        }

        for (int i = RULE_SIZE; i < RULE_SIZE + gridSize; i++){
            int last_idx = my_rule.get(i - RULE_SIZE + gridSize).size() - 1;
            for(int j = RULE_SIZE - 1; j >= 0; j--){
                if (last_idx < 0){
                    this.add(new EmptyPane(paneSize,paneSize,true),j,i);
                } else {
                    int rule_number = my_rule.get(i - RULE_SIZE + gridSize).get(last_idx);
                    this.add(new NumberPane(paneSize,paneSize,rule_number),j,i);
                    last_idx--;
                }
            }
        }
    }
    private void initCellPane(){
        for(int i = 0; i < gridSize; ++i){
            for(int j =0; j < gridSize; ++j){
                this.add(new CellPane(paneSize,paneSize,i ,j),j+RULE_SIZE,i + RULE_SIZE);
            }
        }
    }

}
