package application;

import gui.ControlPane;
import gui.GamePane;
import io.MapParser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage)  {
        HBox root = new HBox();
        root.setPadding(new Insets(10));

        root.setPrefHeight(600);
        root.setPrefWidth(1000);

        GamePane gamePane = GamePane.getInstance();
        ControlPane controlPane = ControlPane.getInstance();
        root.getChildren().addAll(gamePane,controlPane);
        root.setBackground(new Background(new BackgroundFill(Color.MINTCREAM, null, null)));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Nonogram");
        primaryStage.getIcons().add(new Image("file:res/images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
