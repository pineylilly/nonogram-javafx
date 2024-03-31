package gui;

import javafx.event.EventHandler;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.GameSystem;

public class CellPane extends Pane {

    private static final Image selected_img = new Image(ClassLoader.getSystemResource("images/icon.png").toString());
    private static final Image mark_img = new WritableImage(selected_img.getPixelReader(),64,64,60,60);
    private static final Image X_mark_img = new WritableImage(selected_img.getPixelReader(),129,64,60,60);

    private int row;
    private int col;
    public CellPane(double width,double height,int row,int col){
        super();
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        setRow(row);
        setCol(col);
        fetchPaneImageState();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleOnClick();

            }
        });
    }

    private boolean getMarkState(){
        return GameSystem.getInstance().getMarkState(row,col);
    }

    private void fetchPaneImageState(){
        if (getMarkState()){
            this.setBackground(new Background(new BackgroundImage(mark_img,null,null,null,null)));
        } else {
            this.setBackground(new Background(new BackgroundImage(X_mark_img,null,null,null,null)));
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,new BorderWidths(2))));
    }

    private void handleOnClick(){
        GameSystem.getInstance().toggleState(row,col);
        fetchPaneImageState();
        if(GameSystem.getInstance().isFinish())
            System.out.println("Finish map!");
    }


    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
