package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        root.setPadding(new Insets(10));

        root.setPrefHeight(400);
        root.setPrefWidth(800);



        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Nonogram");
        primaryStage.getIcons().add(new Image("file:res/images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
//        System.out.println("Hello World, bro");
        launch(args);
    }
}
