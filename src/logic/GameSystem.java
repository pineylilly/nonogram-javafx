package logic;


import gui.ControlPane;
import gui.GamePane;
import io.MapParser;

import javax.sound.sampled.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameSystem {
    private static GameSystem instance;
    private List<String> mapPaths;
    private List<List<Integer>> currentRule;
    private String currentPath;

    private int mapSize; // size of map is mapSize x mapSize
    private List<Boolean> gameState;
    private List<List<Integer>> markState;      // 0 = white, 1 = selected, 2 = cross
    private static final String PATH_DIR = "res/map/";

    private int step;

    public GameSystem(){
        step = 0;
        try {
            this.mapPaths = getMap();
        } catch (FileNotFoundException e){
            this.mapPaths = null;
            System.out.println("Map game directory is not found so that map paths is null, you should add path in working directory");
        }

    }

    public static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
        }
        return instance;
    }

    public List<List<Integer>> getCurrentRule() {
        return currentRule;
    }

    public boolean loadMap(){
        Random dice = new Random();
        return loadMap(mapPaths.get(dice.nextInt(mapPaths.size())));
    }

    public boolean loadMap(String path){
        //System.out.println(path);
        this.currentPath = path;
        if (!mapPaths.contains(path)) {

            System.out.println("Load Map failed because invalid path");
            return false;
        }
        MapParser parser = new MapParser(path);
        List<List<Integer>> res = parser.read();
        if (res == null || res.size() % 2 != 0) {
            System.out.println("Load Map failed file not found or use odd line number");
            return false;
        }
        this.mapSize = res.size() / 2;
        this.step = 0;
        this.currentRule = res;
        this.gameState = new ArrayList<>(Collections.nCopies(res.size(),false));
        initMarkState();
        return true;
    }


    private List<String> getMap() throws FileNotFoundException {
        ArrayList<String> paths = new ArrayList<String>();
        File dir = new File(PATH_DIR);
        File[] listOfFile = dir.listFiles();
        if (listOfFile == null){
            throw new FileNotFoundException();
        }
        for (File f : listOfFile)
            if (f.isFile())
                paths.add(PATH_DIR + f.getName());
        return paths;

    }
    public void step(){
        step += 1;
    }
    public int getStep(){
        return step;
    }

    public String getMapName(){
        if (currentPath == null || currentPath.isBlank()) return "No map is loaded";
        String[] map_finder =  currentPath.strip().split("/");
        return map_finder[map_finder.length - 1].replaceFirst("\\..*","");
    }

    public String getCurrent_path() {
        return currentPath;
    }

    public boolean isFinish(){
        for (boolean success_Line : gameState){
            if (!success_Line)
                return false;
        }
        return true;
    }

    private void initMarkState(){
        markState = new ArrayList<List<Integer>>();
        for (int i = 0; i < mapSize; ++i){
            markState.add(new ArrayList<Integer>());
            for (int j = 0; j < mapSize; ++j){
                markState.get(i).add(0);
            }
        }
    }

    public void setCellState(int row, int col, int state) {
        if (row < mapSize && col < mapSize){
            markState.get(row).set(col, state);
            updateState(row, col);
        }
    }

    private List<Integer> countRow(int row){
        int count = 0;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int col = 0; col < mapSize; ++col){
            if (markState.get(row).get(col) == 1){
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
            if (markState.get(row).get(col) == 1){
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
    public void updateState(int row,int col){
        List<Integer> count_col = countCol(col);
        List<Integer> count_row = countRow(row);
        if (count_col.equals(currentRule.get(col))){
            gameState.set(col,true);
        } else {
            gameState.set(col,false);
        }
        if (count_row.equals(currentRule.get(mapSize + row))){
            gameState.set(mapSize+row,true);
        } else {
            gameState.set(mapSize+row,false);
        }

        if (GameSystem.getInstance().isFinish())
            ControlPane.getInstance().onComplete();
    }

    public int getMarkState(int row,int col){
        return markState.get(row).get(col);
    }

    public int getMapSize(){
        return this.mapSize;
    }



    public void newGame(){
        if (loadMap()){
            GamePane.getInstance().newGame();
            ControlPane.getInstance().newGame();
        } else {
            GamePane.getInstance().getChildren().clear();
            ControlPane.getInstance().onFailed();
        }
    }

    public void newGame(String map_path){
        if (loadMap(map_path)){
            GamePane.getInstance().newGame();
            ControlPane.getInstance().newGame();
        } else {
            GamePane.getInstance().getChildren().clear();
            ControlPane.getInstance().onFailed();
        }

    }

    public List<String> getMapPaths() {
        return mapPaths;
    }

//    public void print_win_state(){
//        System.out.println("--------------------win state-------------------------");
//        for (boolean e : gameState){
//            if(e)
//                System.out.print("C ");
//            else
//                System.out.print("X ");
//        }
//        System.out.println();
//        System.out.println("--------------------win state-------------------------");
//    }

//    public void print_state(){
//        for (int i = 0; i < mapSize; ++i){
//            for (int j = 0; j < mapSize; ++j){
//                if (markState.get(i).get(j)){
//                    System.out.print("O ");
//                } else {
//                    System.out.print("X ");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println("----------------------------------------------------");
//    }

}

/*
2,0
3,0
4,0
0,1
1,1
2,1
3,1
4,1
0,2
1,2
2,2
3,2
0,3
1,3
2,3
3,3
0,4
1,4
2,4
6,4
7,4
0,5
1,5
2,5
3,5
4,5
5,5
6,5
7,5
0,6
1,6
2,6
3,6
4,6
5,6
6,6
7,6
0,7
1,7
3,7
4,7
5,7
6,7
7,7
8,7
9,7
2,8
3,8
3,9
8,9

 */
