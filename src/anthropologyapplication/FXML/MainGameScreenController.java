/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.FXML;

import anthropologyapplication.AutoMapper.AutoMapperGui;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.AutoMapper.Vector3;
import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import anthropologyapplication.DisplayData.ProductProductionDisplayData;
import anthropologyapplication.DisplayData.WarriorTrainingDisplayData;
import anthropologyapplication.MainGameCode;
import anthropologyapplication.Map;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Duke
 */
public class MainGameScreenController implements Initializable {

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

    AutoMapperGui myAutomapper = new AutoMapperGui();;
    @FXML
    private Label worldDisplayTime;
    @FXML
    private FlowPane BuildingItemList;
    @FXML
    private Label assignCitizenWarriorsCount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int i = 0;
    }    

    String BuildingSelectedForBuilding;
    
    
    MainGameCode myMain;
    public void setMainGameCode(MainGameCode aThis) {
        //myAutomapper.setScreenXYSize((int)CanvasMapDisplay.getWidth(),(int)CanvasMapDisplay.getHeight());
        myMain = aThis;
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        worldDisplayBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        worldDisplayFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        worldDisplayWorkersCount.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        this.assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        //this.assignCitizensWarriorsCount1
        
        Iterator<ProductProductionDisplayData> aProductIterator =  aThis.getPlayersCamp().getProductionHandler().getTradeGoodsAvailable();
        while(aProductIterator.hasNext())
        {
            ProductProductionDisplayData myData = aProductIterator.next();
            Button aButton = new Button(myData.getName());
            aButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                Button myButton = aButton;
                
                @Override
                public void handle(MouseEvent event) {
                    myMain.setProductionFocus(aButton.getText());
                }           
            });
        }
        
        Iterator<WarriorTrainingDisplayData> aWarriorIterator =  aThis.getPlayersCamp().getWarriorHandler().getWarriorsAvailable();
        while(aProductIterator.hasNext())
        {
            WarriorTrainingDisplayData myData = aWarriorIterator.next();
            Button aButton = new Button(myData.getName());
            aButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                Button myButton = aButton;
                
                @Override
                public void handle(MouseEvent event) {
                    myMain.addWarrior(aButton.getText());
                }           
            });
        }
        
        
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        Iterator<BuildingConstructionDisplayData> anIterator = aThis.getPlayersCamp().getBuildingHandler().getBuildable().iterator();
        while(anIterator.hasNext())
        {
            BuildingConstructionDisplayData myData = anIterator.next();
            Button aButton = new Button(myData.getName()); //For now use button later override it
            aButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                Button myButton = aButton;
                
                @Override
                public void handle(MouseEvent event) {
                    BuildingSelectedForBuilding = aButton.getText();
                }           
            });
            BuildingItemList.getChildren().add(aButton);
        }
        
        
    }

    
    
    
    
    
    
    
    public void setMapData(Map mapData) {
        
        myAutomapper.setMap(mapData, myMain);
    }

    public void setTime(String aTime) {
        worldDisplayTime.setText(aTime);
    }
    
    
    @FXML
    private void CanvasMapDisplayClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY && BuildingSelectedForBuilding != null)
        {
            BuildingSelectedForBuilding = null;
        } else if (event.getButton() == MouseButton.PRIMARY && BuildingSelectedForBuilding != null){
           MapTile aTile = myAutomapper.getTileAtMouseCoordinates(event.getSceneX() - CanvasMapDisplay.getLayoutX(), event.getSceneY() - CanvasMapDisplay.getLayoutY());
           if(aTile != null)
           {
                BuildingConstructionDisplayData myConstruction = myMain.getPlayersCamp().getBuildingHandler().startBuilding(BuildingSelectedForBuilding, aTile);
                if(myConstruction != null)
                {
                    BuildingQueueList.getChildren().add(new QueueButton(myConstruction));
                }
           }
        }
    }

    private void MouseDraggingDetectedOnCanvas(MouseEvent event) {
        int i = 0;
    }

    public void updateMap() {
        myAutomapper.setScreenXYSize((int)CanvasMapDisplay.getWidth(), (int)CanvasMapDisplay.getHeight());
        myAutomapper.setCanvas(CanvasMapDisplay.getGraphicsContext2D(), CanvasMapDisplay.getWidth(), CanvasMapDisplay.getHeight());
    }

    public void redrawMap()
    {
        
        //myAutomapper.setCanvas(CanvasMapDisplay.getGraphicsContext2D(), CanvasMapDisplay.getWidth(), CanvasMapDisplay.getHeight());
        myAutomapper.Draw();
    }
    
    @FXML
    private void increaseWorkersButtonClick(ActionEvent event) {
        myMain.increaseWorkers();
        worldDisplayWorkersCount.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
    }

    @FXML
    private void decreaseWorkersButtonClick(ActionEvent event) {
        myMain.decreaseWorkers();
        worldDisplayWorkersCount.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
    }

    @FXML
    private void increaseWarriorsButtonClick(ActionEvent event) {
        myMain.increaseWarriors();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        worldDisplayWarriorsCount.setText("" + myMain.getPlayersCamp().getWarriorHandler().getWarriorsAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizenWarriorsCount.setText("" + myMain.getPlayersCamp().getWarriorHandler().getWarriorsAmount());
    }

    @FXML
    private void decreaseWarriorsButtonClick(ActionEvent event) {
        myMain.decreaseWarriors();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        worldDisplayWarriorsCount.setText("" + myMain.getPlayersCamp().getWarriorHandler().getWarriorsAmount());
        assignCitizenWarriorsCount.setText("" + myMain.getPlayersCamp().getWarriorHandler().getWarriorsAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
    }

    @FXML
    private void increaseBuildersButtonClick(ActionEvent event) {
        
        myMain.increaseBuilders();
        worldDisplayBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
    }

    @FXML
    private void increaseFarmersButtonClick(ActionEvent event) {
        myMain.increaseFarmers();
        worldDisplayFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
    }

    @FXML
    private void fastForwardClicked(ActionEvent event) {
        myMain.accelerateTime();
    }

    @FXML
    private void clickedNormalSpeed(ActionEvent event) {
        myMain.decelerateTime();
    }

    @FXML
    private void openPauseMenu(ActionEvent event) {
        myMain.pauseGame();
    }

    @FXML
    private void CanvasMapTrackMouseReleased(MouseEvent event) {
        Dragging = false;
    }

    boolean Dragging = false;
    double MouseScreenX;
    double MouseScreenY;
    double startMouseScreenX;
    double startMouseScreenY;
    @FXML
    private void CanvasMapTrackMouseHeld(MouseEvent event) {
        startMouseScreenX = event.getSceneX();
        startMouseScreenY = event.getSceneY();
        Dragging = true;
    }

    private void MousedMovedWithinMe(MouseEvent event) {
        MapTile aTile = this.myAutomapper.getTileAtMouseCoordinates(event.getSceneX(), event.getSceneY());
        displayMapInfo.setText(aTile.toString());
    }

    @FXML
    private void MouseDraggedMe(MouseEvent event) {
        
            BuildingSelectedForBuilding = null;//to prevent the thing from thinking the user clicked somethign;
          MouseScreenX = event.getSceneX();
            MouseScreenY = event.getSceneY();
            int TileWidth = myAutomapper.getTileWidth();
            int TileHeight = myAutomapper.getTileHeight();
            Vector3 Coordinates = myAutomapper.getCurrentRoomCoordinates();
            if((MouseScreenX) < startMouseScreenX - TileWidth)
            {
                Coordinates = Coordinates.Transform(new Vector3(1,0,0));    
            }
            else if (MouseScreenX > startMouseScreenX + TileWidth)
            {
                Coordinates = Coordinates.Transform(new Vector3(-1,0,0));
            }
            if((MouseScreenY) < startMouseScreenY - TileHeight)
            {
               Coordinates = Coordinates.Transform(new Vector3(0,1,0));
            }
            else if((MouseScreenY) > startMouseScreenY + TileHeight) 
            {
                 Coordinates = Coordinates.Transform(new Vector3(0,-1,0));
            }
            if(Coordinates != myAutomapper.getCurrentRoomCoordinates())
            {
                if(myAutomapper.getRoomAtCoordinate(Coordinates) != null)
                {
                    startMouseScreenX = MouseScreenX;
                    startMouseScreenY = MouseScreenY;
                    myAutomapper.setRoomFocus(myAutomapper.getRoomAtCoordinate(Coordinates).getCoordinates());

                }
            }
    }

    @FXML
    private void MouseMovedWithinMe(MouseEvent event) {
    }

    @FXML
    private void FastForwardButtonClicked(MouseEvent event) {
        myMain.accelerateTime();
    }

    @FXML
    private void RegularSpeedButtonClicked(MouseEvent event) {
        myMain.decelerateTime();
    }

    @FXML
    private void CanvasScrollingFinished(ScrollEvent event) {
        System.out.println(event.getDeltaX());
    }

    @FXML
    private void CanvasScrollingStarted(ScrollEvent event) {
        System.out.println(event.getDeltaX());
    }

    @FXML
    private void CanvasScrollingWhileScrolling(ScrollEvent event) {
    }

    public AutoMapperGui getAutomap() {
        return this.myAutomapper;
    }

    private void setScreenXYSize(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void decreaseBuildersButtonClick(ActionEvent event) {
        this.myMain.decreaseBuilders();
        worldDisplayBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
    }

    @FXML
    private void decreaseFarmersButtonClick(ActionEvent event) {
        
        this.myMain.decreaseFarmers();
        
        worldDisplayFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
    }

    public void setMapMessage(String aMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void showErrorMessage(String ErrorString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
}
