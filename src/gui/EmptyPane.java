package gui;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EmptyPane extends StackPane {
    public EmptyPane(double width,double height,boolean useBorder){
        super();
        setPrefWidth(width);
        setPrefHeight(height);
        if (useBorder)
            this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,new BorderWidths(2))));

    }
}
