package gui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.GameSystem;

public class CellPane extends Pane {

    private static final Image BUFFER_IMG = new Image(ClassLoader.getSystemResource("images/icon.png").toString());
    private static Image mark_img = new WritableImage(BUFFER_IMG.getPixelReader(),64,64,60,60);
    private static Image x_mark_img = new WritableImage(BUFFER_IMG.getPixelReader(),129,64,60,60);

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
            this.setBackground(new Background(new BackgroundImage(x_mark_img,null,null,null,null)));
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,new BorderWidths(2))));
    }

    private void handleOnClick(){
        if (!GameSystem.getInstance().isFinish()) {
            GameSystem.getInstance().step();
            ControlPane.getInstance().step();
            GameSystem.getInstance().toggleState(row, col);
            fetchPaneImageState();
            if (GameSystem.getInstance().isFinish())
                ControlPane.getInstance().onComplete();
        }
    }

    private static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }

    public static void scale_static_image(int targetWidth,int targetHeight){
        mark_img = scale(mark_img,targetWidth,targetHeight,true);
        x_mark_img =  scale(x_mark_img,targetWidth,targetHeight,true);
    }


    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
