/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import anthropologyapplication.DisplayData.WarriorTrainingDisplayData;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Duke
 */
public class QuickTipsWarriorTraining extends javafx.scene.control.Button   {
    WarriorTrainingDisplayData myConstruction;
    Tooltip myToolTip;
    QuickTipsWarriorTraining toPass;
    QuickTipsWarriorTraining(WarriorTrainingDisplayData myData, Tooltip tooltip) {
        super(myData.getName());
        toPass = this;
        setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            Bounds WindowBounds = boundsInLocalProperty().get();

            Point2D p = localToScreen(WindowBounds.getMaxX(), WindowBounds.getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)
            myToolTip.show(toPass, p.getX(), p.getY());
            }
        });
        
        setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                myToolTip.hide();
            }
        });
        this.myConstruction = myConstruction;
        myToolTip = tooltip;
    }
    
}
