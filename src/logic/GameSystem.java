package logic;


import gui.ControlPane;
import gui.GamePane;
import gui.MapSearchPane;
import io.MapParser;

import javax.sound.sampled.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameSystem {
    private static final String PATH_DIR = "map/";
    private static GameSystem instance;
    private List<String> mapPaths;
    private List<List<Integer>> currentRule;
    private String currentPath;

    private int mapSize; // size of map is mapSize x mapSize
    private List<Boolean> ruleStates;
    private List<List<Integer>> cellStates;      // 0 = blank, 1 = filled, 2 = marked (X)

    private int step;

    public GameSystem(){
        this.step = 0;
        try {
            this.mapPaths = getMap();
        } catch (FileNotFoundException e){
            this.mapPaths = null;
        }
    }

    public static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
        }
        return instance;
    }

    public boolean loadMap(){
        Random dice = new Random();
        return loadMap(mapPaths.get(dice.nextInt(mapPaths.size())));
    }

    public boolean loadMap(String path){
        //System.out.println(path);
        this.currentPath = path;
        if (!mapPaths.contains(path)) {
            return false;
        }
        MapParser parser = new MapParser(path);
        List<List<Integer>> res = parser.read();
        if (res == null || res.size() % 2 != 0) {
            return false;
        }
        this.mapSize = res.size() / 2;
        this.step = 0;
        this.currentRule = res;
        this.ruleStates = new ArrayList<>(Collections.nCopies(res.size(),false));
        MapSearchPane.getInstance().setMapSelectorValue(this.currentPath);
        initCellStates();
        return true;
    }

    private List<String> getMap() throws FileNotFoundException {
        ArrayList<String> paths = new ArrayList<String>();
//        File dir = new File(ClassLoader.getSystemResource(PATH_DIR).getPath());
//        System.out.println(dir.isDirectory());
//        File[] listOfFile = dir.listFiles();
//        if (listOfFile == null){
//            throw new FileNotFoundException();
//        }
//        for (File f : listOfFile)
//            if (f.isFile())
//                paths.add(PATH_DIR + f.getName());
//        return paths;
        String[] filenames = new String[] {"easy01.txt", "easy02.txt", "easy03.txt", "easy04.txt", "easy05.txt", "hard01.txt", "hard02.txt", "hard03.txt", "hard04.txt", "hard05.txt", "heart.txt", "never_gonna_give_you_up.txt"};
        for (String s : filenames) {
            paths.add(PATH_DIR + s);
        }
        return paths;

    }
    public void step(){
        step += 1;
        ControlPane.getInstance().step();
    }

    public String getMapName(){
        if (currentPath == null || currentPath.isBlank()) return null;
        String[] map_finder =  currentPath.strip().split("/");
        return map_finder[map_finder.length - 1].replaceFirst("\\..*","");
    }

    private void initCellStates(){
        cellStates = new ArrayList<List<Integer>>();
        for (int i = 0; i < mapSize; ++i){
            cellStates.add(new ArrayList<Integer>());
            for (int j = 0; j < mapSize; ++j){
                cellStates.get(i).add(0);
            }
        }
    }

    public void setCellState(int row, int col, int state) {
        if (row < mapSize && col < mapSize){
            cellStates.get(row).set(col, state);
            updateRuleStates(row, col);
        }
    }

    private List<Integer> countRow(int row){
        int count = 0;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int col = 0; col < mapSize; ++col){
            if (cellStates.get(row).get(col) == 1){
                count++;
            } else {
                if (count != 0)
                    result.add(count);
                count = 0;
            }
        }
        if (count != 0) {
            result.add(count);
            count = 0;
        }
        return result;
    }

    private List<Integer> countCol(int col){
        int count = 0;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int row = 0; row < mapSize; ++row){
            if (cellStates.get(row).get(col) == 1){
                count++;
            } else {
                if (count != 0)
                    result.add(count);
                count = 0;
            }
        }
        if (count != 0) {
            result.add(count);
        }
        return result;
    }
    public void updateRuleStates(int row,int col){
        List<Integer> count_col = countCol(col);
        List<Integer> count_row = countRow(row);
        if (count_col.equals(currentRule.get(col))){
            ruleStates.set(col,true);
        } else {
            ruleStates.set(col,false);
        }
        if (count_row.equals(currentRule.get(mapSize + row))){
            ruleStates.set(mapSize+row,true);
        } else {
            ruleStates.set(mapSize+row,false);
        }

        if (GameSystem.getInstance().isFinish())
            ControlPane.getInstance().onComplete();
    }

    public void newGame(){
        if (loadMap()){
            GamePane.getInstance().newGame();
            ControlPane.getInstance().resetText();
        } else {
            GamePane.getInstance().getChildren().clear();
            ControlPane.getInstance().onFailed();
        }
    }

    public void newGame(String mapPath){
        if (loadMap(mapPath)){
            GamePane.getInstance().newGame();
            ControlPane.getInstance().resetText();
        } else {
            GamePane.getInstance().getChildren().clear();
            ControlPane.getInstance().onFailed();
        }

    }

    public boolean isFinish(){
        for (boolean success_Line : ruleStates){
            if (!success_Line)
                return false;
        }
        return true;
    }

    public int getStep(){
        return step;
    }

    public int getCellState(int row,int col){
        return cellStates.get(row).get(col);
    }

    public int getMapSize(){
        return this.mapSize;
    }

    public List<List<Integer>> getCurrentRule() {
        return currentRule;
    }

    public List<String> getMapPaths() {
        return mapPaths;
    }

}
