/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import anthropologyapplication.DisplayData.DisplayData;
import anthropologyapplication.MainGameCode;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author noone
 */
public class customProgressBar extends javafx.scene.control.ProgressBar {
    private Label myLabel;
    private final DisplayData ProgressData;
    private Tooltip myToolTip;
    boolean isDoneNow = false;
    MainGameCode myCode;
    public boolean isDone()
    {
        return isDoneNow;
    }
    
    //private final MainGameCode CodeObject;
    public customProgressBar(DisplayData myData, MainGameCode myCode)
    {
        this.myCode = myCode;
        //CodeObject = myCode;
        this.ProgressData = myData;
        setProgress(0);
        myToolTip = new Tooltip(myData.getToolTipString());
        myLabel = new Label();
        myLabel.setText(ProgressData.getName());
        Bounds WindowBounds = boundsInLocalProperty().get();
        //myLabel.setTranslateX(WindowBounds.getWidth()*0.5 - myLabel.getBoundsInLocal().getWidth()*0.5);
        //myLabel.setTranslateY(WindowBounds.getHeight()*0.5 - myLabel.getBoundsInLocal().getHeight()*0.5);
        myToolTip.setText(myData.getTimeToCompleteString());
        customProgressBar toPass = this;
        setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            Bounds WindowBounds = boundsInLocalProperty().get();
            Point2D p = localToScreen(WindowBounds.getMaxX(), WindowBounds.getMaxY()); 
            myToolTip.show(toPass, p.getX(), p.getY());
            }
        });
        setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == event.getButton().SECONDARY)
                {
                    ProgressData.acceptRemoverAsVisitor(myCode);//Pass as a visitor
                    isDoneNow = true;
                }
            }
            
        });
        
        setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                myToolTip.hide();
            }
        });
        //Programmatically create Label over the bar
    }
    
    public void update()
    {
        myToolTip.setText(ProgressData.getTimeToCompleteString());
        if(ProgressData.getCompletionPercentage() == 1.0D && !isDoneNow)
        {
            isDoneNow = (ProgressData.getCompletionPercentage() == 1.0D);
            this.setProgress(ProgressData.getCompletionPercentage());
        } else if(ProgressData.getCompletionPercentage() != 1.0D) {
            this.setProgress(ProgressData.getCompletionPercentage());
        }
    }
    
}
