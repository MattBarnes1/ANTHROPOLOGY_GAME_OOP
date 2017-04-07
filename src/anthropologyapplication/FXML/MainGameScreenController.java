/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.AutoMapper.AutoMapperGui;
import anthropologyapplication.MainGameCode;
import anthropologyapplication.Map;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private Label assignCitizensFreeCitizensCount;
    @FXML
    private Label GameWorldTimeObject112;
    @FXML
    private Label assignCitizensWorkersCount1;
    @FXML
    private Label GameWorldTimeObject1112;
    @FXML
    private Label assignCitizensWarriorsCount1;
    @FXML
    private Label GameWorldTimeObject11112;
    @FXML
    private Label assignCitizensBuildersCount;
    @FXML
    private Label GameWorldTimeObject111111;
    @FXML
    private Label assignCitizensFarmersCount;
    @FXML
    private Button increaseWorkersButton;
    @FXML
    private Button decreaseWorkersButton;
    @FXML
    private Button increaseWarriorsButton;
    @FXML
    private Button decreaseWarriorsButton;
    @FXML
    private Button increaseBuildersButton;
    @FXML
    private Button increaseFarmersButton;
    @FXML
    private Button decreaseFarmersButton;
    @FXML
    private Label GameWorldTimeObject11;
    @FXML
    private Label GameWorldTimeObject111;
    @FXML
    private Label GameWorldTimeObject1111;
    @FXML
    private Label worldDisplayFreeCitizensCount;
    @FXML
    private Label worldDisplayWarriorsCount;
    @FXML
    private Label worldDisplayBuildersCount;
    @FXML
    private Label worldDisplayWorkersCount;
    @FXML
    private Button NormalSpeedButton;
    @FXML
    private Button Pause;
    @FXML
    private Label GameWorldTimeObject11111;
    @FXML
    private Label worldDisplayFarmersCount;
    @FXML
    private VBox BuildingQueueList;

    AutoMapperGui myAutomapper;
    @FXML
    private Label worldDisplayTime;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    MainGameCode myMain;
    public void setMainGameCode(MainGameCode aThis) {
        myMain = aThis;
        Iterator<String> anIterator = aThis.getPlayersCamp().getBuildingHandler().getBuildable().iterator();
        //TODO: Iterate through and add buttons to building Handler
    }

    public void setMapData(Map mapData) {
        myAutomapper = new AutoMapperGui();
        myAutomapper.setMap(mapData,(int)CanvasMapDisplay.getWidth(), (int)CanvasMapDisplay.getHeight());
        myAutomapper.setCanvas(CanvasMapDisplay.getGraphicsContext2D(), CanvasMapDisplay.getWidth(), CanvasMapDisplay.getHeight());
    }

    public void setTime(String aTime) {
        worldDisplayTime.setText(aTime);
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

    public void redrawMap()
    {
        myAutomapper.Draw();
    }
    
    @FXML
    private void increaseWorkersButtonClick(ActionEvent event) {
    }

    @FXML
    private void decreaseWorkersButtonClick(ActionEvent event) {
    }

    @FXML
    private void increaseWarriorsButtonClick(ActionEvent event) {
    }

    @FXML
    private void decreaseWarriorsButtonClick(ActionEvent event) {
    }

    @FXML
    private void increaseBuildersButtonClick(ActionEvent event) {
    }

    @FXML
    private void increaseFarmersButtonClick(ActionEvent event) {
    }

    @FXML
    private void fastForwardClicked(ActionEvent event) {
    }

    @FXML
    private void clickedNormalSpeed(ActionEvent event) {
    }

    @FXML
    private void openPauseMenu(ActionEvent event) {
    }

    

    
}
