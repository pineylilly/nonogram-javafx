package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.Scanner;

public class MapParser {
    private String filename;
    public MapParser(){

    }

    public MapParser(String filename){
        setFilename(filename);
    }

    public ArrayList<ArrayList<Integer>> read(){
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        File obj = new File(filename);
        try {
            Scanner reader = new Scanner(obj);
            int idx = 0;
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                if (line.isBlank()) continue;
                result.add(new ArrayList<Integer>());

                for (String data : line.split(",")){
                    result.get(idx).add(Integer.parseInt(data));
                }
                idx++;
            }
            return result;
        } catch (FileNotFoundException e){
            System.out.println("File is not found. try to change env path or set new correct file path");
            return null;
        }

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
