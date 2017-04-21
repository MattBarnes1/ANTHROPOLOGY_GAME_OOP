/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author Duke
 */
public class QuickTipsBuildButton extends javafx.scene.control.Button  {
    
    Insets myButtonInsets;
    BuildingConstructionDisplayData myConstruction;
    Tooltip myToolTip;
    QuickTipsBuildButton toPass;
    QuickTipsBuildButton(BuildingConstructionDisplayData myConstruction, Tooltip myTip) {
        super(myConstruction.getName());
        
        toPass = this;
        setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            Bounds WindowBounds = boundsInLocalProperty().get();

            Point2D p = localToScreen(WindowBounds.getMaxX(), WindowBounds.getMaxY()); //get from the window coordinates to the screens coordinates;
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
        myToolTip = myTip;
    }

    public void start()
    {
       
        this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, new Insets(myButtonInsets.getTop(), myButtonInsets.getRight()*myConstruction.getCompletionPercentage(), myButtonInsets.getLeft(), myButtonInsets.getBottom()))));
    }
    
    public void update()
    {
        System.out.println(myConstruction.getCompletionPercentage());
        
        myButtonInsets = this.getInsets();
        this.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, new Insets(myButtonInsets.getTop()*myConstruction.getCompletionPercentage(), myButtonInsets.getRight(), myButtonInsets.getLeft(), myButtonInsets.getBottom()))));
    }
            
    
    
    QuickTipsBuildButton(BuildingConstructionDisplayData myConstruction) {
        super(myConstruction.getName());
        toPass = this;
        this.myConstruction = myConstruction;
    }

    boolean isDone() {
        return (myConstruction.getCompletionPercentage() == 1);
    }

   
    
   
    
}
