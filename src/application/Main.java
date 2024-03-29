package application;

import io.MapParser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.GameSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Main{
    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        root.setPadding(new Insets(10));

        root.setPrefHeight(600);
        root.setPrefWidth(1000);



        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Nonogram");
        primaryStage.getIcons().add(new Image("file:res/images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();





    }
    */
    public static void main(String[] args) {
        GameSystem game = GameSystem.getInstance();
        game.loadMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println(game.getCurrent_path());
        while(!game.isFinish()){
            game.print_state();
            System.out.print("selct row and col as (row,col) : ");
            String command = scanner.nextLine();
            String[] cmd = command.strip().replaceAll("[)(]","").split(",");
            if (cmd.length != 2) {
                System.out.println("Invalid command");
                continue;
            } else {
                try {
                    int row = Integer.parseInt(cmd[0]);
                    int col = Integer.parseInt(cmd[1]);
                    game.toggleState(row,col);
                    game.print_win_state();
                } catch (Exception e){
                    continue;
                }
            }
        }
        System.out.print("You win!");
        //launch(args);
    }
}
