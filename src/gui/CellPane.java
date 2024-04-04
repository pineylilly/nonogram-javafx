package gui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import logic.GameSystem;

public class CellPane extends Pane {

    private static final Image BUFFER_IMG = new Image(ClassLoader.getSystemResource("images/icon.png").toString());
    private static final Image X_MARK_IMG = new WritableImage(BUFFER_IMG.getPixelReader(),129,64,60,60);

    private int row;
    private int col;
    public CellPane(double width, double height, int row, int col){
        super();
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,new BorderWidths(1))));
        setRow(row);
        setCol(col);
        setCellImage();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleOnClick(mouseEvent);
            }
        });
    }

    private void setCellImage(){
        if (getCellState() == 0){
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        } else if (getCellState() == 1) {
            this.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
        } else {
            this.setBackground(new Background(new BackgroundImage(X_MARK_IMG, null, null, null, new BackgroundSize(this.getWidth(), this.getHeight(), false, false, false, false))));
        }
    }

    private void handleOnClick(MouseEvent mouseEvent){
        if (!GameSystem.getInstance().isFinish()) {
            if (!mouseEvent.getButton().equals(MouseButton.PRIMARY) && !mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                return;
            }

            GameSystem.getInstance().step();

            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (getCellState() == 1) {
                    GameSystem.getInstance().setCellState(row, col, 0);
                }
                else {
                    GameSystem.getInstance().setCellState(row, col, 1);
                }
            }
            else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                if (getCellState() == 2) {
                    GameSystem.getInstance().setCellState(row, col, 0);
                }
                else {
                    GameSystem.getInstance().setCellState(row, col, 2);
                }
            }

            setCellImage();
        }
    }

    private int getCellState(){
        return GameSystem.getInstance().getCellState(row, col);
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
