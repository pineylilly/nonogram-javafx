package gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NumberPane extends EmptyPane{
    private int number;
    public NumberPane(double width,double height,int number){
        super(width,height,true);
        Text text = new Text(number+ "");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD,width / 2));
        this.getChildren().add(text);

    }
}
