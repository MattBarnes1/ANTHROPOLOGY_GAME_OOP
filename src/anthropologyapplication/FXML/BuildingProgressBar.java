/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
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
public class BuildingProgressBar extends javafx.scene.control.ProgressBar {
    private Label myLabel;
    private final BuildingConstructionDisplayData BuildingData;
    private Tooltip myToolTip;
    //private final MainGameCode CodeObject;
    public BuildingProgressBar(BuildingConstructionDisplayData myData, MainGameCode myCode)
    {
        //CodeObject = myCode;
        this.BuildingData = myData;
        myLabel.setText(BuildingData.getName());
        Bounds WindowBounds = boundsInLocalProperty().get();
        myLabel.setTranslateX(WindowBounds.getWidth()*0.5 - myLabel.getBoundsInLocal().getWidth()*0.5);
        myLabel.setTranslateY(WindowBounds.getHeight()*0.5 - myLabel.getBoundsInLocal().getHeight()*0.5);
        myToolTip.setText(myData.getTimeToCompleteString());
        BuildingProgressBar toPass = this;
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
                if(event.isSecondaryButtonDown())
                {
                    myCode.removeBuildingFromConstruction(myData);
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
        if(BuildingData.getCompletionPercentage() == 1.0D)
        {
            
        }
    }
    
}
