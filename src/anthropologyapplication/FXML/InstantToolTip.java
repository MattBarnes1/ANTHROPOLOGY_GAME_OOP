/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Skin;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Duke
 */
public class InstantToolTip extends Tooltip {
    public InstantToolTip(String ToolTip)
    {
        super.setText(ToolTip);
        super.wrapTextProperty().set(true);
        super.getOwnerNode().setsetOnMouseEntered(OnMouseEnter);
    }
    
    public void OnMouseEnter(MouseEvent anEvent)
    {
        Bounds WindowBounds = this.getOwnerNode().boundsInLocalProperty().get();
        
        Point2D p = this.getOwnerNode().localToScreen(WindowBounds.getMaxX(), WindowBounds.getMaxY()); //I position the tooltip at bottom right of the node (see below for explanation)
        this.show(this.getOwnerNode(), p.getX(), p.getY());
    }
    
    public void OnMouseExit(MouseEvent anEvent)
    {
        this.hide();
    }
    
    
}
