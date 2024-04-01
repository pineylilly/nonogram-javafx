package gui;

import javafx.scene.layout.GridPane;
import logic.GameSystem;
import java.util.List;
public class GamePane extends GridPane {

    private int grid_size;
    private static final int GAME_SIZE = 620;
    private static final int RULE_SIZE = 4;
    private double pane_size;

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
        setGrid_size(GameSystem.getInstance().getMap_size());
        pane_size = GAME_SIZE / ((grid_size +RULE_SIZE) * 1.0);
        CellPane.scale_static_image((int) (pane_size),(int) (pane_size));
        initEmptyPane();
        initRulePane();
        initCellPane();
    }

    public int getGrid_size() {
        return grid_size;
    }

    public void setGrid_size(int grid_size) {
        this.grid_size = grid_size;
    }

    private void initEmptyPane(){
        for(int i = 0; i < RULE_SIZE; ++i){
            for(int j = 0; j < RULE_SIZE; ++j){
                this.add(new EmptyPane(pane_size,pane_size,false),j,i);
            }
        }
    }



    private void initRulePane(){
        List<List<Integer>> my_rule = GameSystem.getInstance().getCurrent_rule();
        for (int j = RULE_SIZE; j < (RULE_SIZE + grid_size); j++){
            int last_idx = my_rule.get(j - RULE_SIZE).size() - 1;
            for(int i = RULE_SIZE - 1; i >= 0; i--){
                if (last_idx < 0) {
                    this.add(new EmptyPane(pane_size,pane_size,true),j,i);
                } else {
                    int rule_number = my_rule.get(j-RULE_SIZE).get(last_idx);
                    this.add(new NumberPane(pane_size,pane_size,rule_number),j,i);
                    last_idx--;
                }
            }
        }

        for (int i = RULE_SIZE; i < RULE_SIZE + grid_size; i++){
            int last_idx = my_rule.get(i - RULE_SIZE + grid_size).size() - 1;
            for(int j = RULE_SIZE - 1; j >= 0; j--){
                if (last_idx < 0){
                    this.add(new EmptyPane(pane_size,pane_size,true),j,i);
                } else {
                    int rule_number = my_rule.get(i - RULE_SIZE + grid_size).get(last_idx);
                    this.add(new NumberPane(pane_size,pane_size,rule_number),j,i);
                    last_idx--;
                }
            }
        }
    }
    private void initCellPane(){
        for(int i = 0; i < grid_size; ++i){
            for(int j =0; j < grid_size; ++j){
                this.add(new CellPane(pane_size,pane_size,i ,j),j+RULE_SIZE,i + RULE_SIZE);
            }
        }
    }

}
