/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.MainGameCode;
import anthropologyapplication.Map;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class MainGameScreenController implements Initializable {

    @FXML
    private FlowPane BuildingItem;
    @FXML
    private FlowPane TradeFlowpane;
    @FXML
    private Canvas CanvasMapDisplay;
    @FXML
    private Label GameWorldTimeObject;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int debug = 0;
    }    

    MainGameCode myMain;
    public void setMainGameCode(MainGameCode aThis) {
        myMain = aThis;
        Iterator<String> anIterator = aThis.getPlayersCamp().getBuildingHandler().getBuildable().iterator();
        //TODO: Iterate through and add buttons to building Handler
    }

    public void setMapData(Map mapData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setTime(String aTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void CanvasMapDisplayClicked(MouseEvent event) {
    }

    @FXML
    private void MouseDraggingDetectedOnCanvas(MouseEvent event) {
    }

    public void updateMap(int xDirectionShift, int yDirectionShift) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
}
