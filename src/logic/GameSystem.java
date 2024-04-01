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

    public List<String> getMap_paths() {
        return map_paths;
    }

    private List<String> map_paths;
    private List<List<Integer>> current_rule;
    private String current_path;

    private int map_size; // size of map is map_size x map_size
    private List<Boolean> game_state;
    private List<List<Boolean>> mark_state;
    private static final String PATH_DIR = "res/map/";

    private int step;

    public GameSystem(){
        step = 0;
        try {
            this.map_paths = getMap();
        } catch (FileNotFoundException e){
            this.map_paths = null;
            System.out.println("Map game directory is not found so that map paths is null, you should add path in working directory");
        }

    }

    public static GameSystem getInstance() {
        if (instance == null) {
            instance = new GameSystem();
        }
        return instance;
    }

    public List<List<Integer>> getCurrent_rule() {
        return current_rule;
    }

    public boolean loadMap(){
        Random dice = new Random();
        return loadMap(map_paths.get(dice.nextInt(map_paths.size())));
    }

    public boolean loadMap(String path){
        //System.out.println(path);
        this.current_path = path;
        if (!map_paths.contains(path)) {

            System.out.println("Load Map failed because invalid path");
            return false;
        }
        MapParser parser = new MapParser(path);
        List<List<Integer>> res = parser.read();
        if (res == null || res.size() % 2 != 0) {
            System.out.println("Load Map failed file not found or use odd line number");
            return false;
        }
        this.map_size = res.size() / 2;
        this.step = 0;
        this.current_rule = res;
        this.game_state = new ArrayList<>(Collections.nCopies(res.size(),false));
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
        if (current_path == null || current_path.isBlank()) return "No map is loaded";
        String[] map_finder =  current_path.strip().split("/");
        return map_finder[map_finder.length - 1].replaceFirst("\\..*","");
    }

    public String getCurrent_path() {
        return current_path;
    }

    public boolean isFinish(){
        for (boolean success_Line : game_state){
            if (!success_Line)
                return false;
        }
        return true;
    }

    private void initMarkState(){
        mark_state = new ArrayList<List<Boolean>>();
        for (int i = 0; i < map_size; ++i){
            mark_state.add(new ArrayList<Boolean> ());
            for (int j = 0; j < map_size; ++j){
                mark_state.get(i).add(true);
            }
        }
    }

    public boolean toggleState(int row,int col){
        if (row < map_size && col < map_size){
            boolean state = mark_state.get(row).get(col);
            mark_state.get(row).set(col,!state);
            update_state(row,col);
            return true;
        } else {
            return false;
        }
    }

    private List<Integer> countRow(int row){
        int count = 0;
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int col = 0; col < map_size; ++col){
            if (mark_state.get(row).get(col)){
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
        for (int row = 0; row < map_size; ++row){
            if (mark_state.get(row).get(col)){
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
    public void update_state(int row,int col){
        List<Integer> count_col = countCol(col);
        List<Integer> count_row = countRow(row);
        if (count_col.equals(current_rule.get(col))){
            game_state.set(col,true);
        } else {
            game_state.set(col,false);
        }
        if (count_row.equals(current_rule.get(map_size + row))){
            game_state.set(map_size+row,true);
        } else {
            game_state.set(map_size+row,false);
        }
    }

    public boolean getMarkState(int row,int col){
        return mark_state.get(row).get(col);
    }

    public int getMap_size(){
        return this.map_size;
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

    public void print_win_state(){
        System.out.println("--------------------win state-------------------------");
        for (boolean e : game_state){
            if(e)
                System.out.print("C ");
            else
                System.out.print("X ");
        }
        System.out.println();
        System.out.println("--------------------win state-------------------------");
    }

    public void print_state(){
        for (int i = 0; i < map_size; ++i){
            for (int j = 0; j < map_size; ++j){
                if (mark_state.get(i).get(j)){
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }

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
