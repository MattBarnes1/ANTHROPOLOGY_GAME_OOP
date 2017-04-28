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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

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
    private Label GameWorldTimeObject112;
    @FXML
    private Label assignCitizensWorkersCount1;
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
    private Button increaseBuildersButton;
    @FXML
    private Button increaseFarmersButton;
    @FXML
    private Button decreaseFarmersButton;
    @FXML
    private Label worldDisplayFreeCitizensCount;
    @FXML
    private Button NormalSpeedButton;
    @FXML
    private Button Pause;
    @FXML
    private VBox BuildingQueueList;

    AutoMapperGui myAutomapper = new AutoMapperGui();;
    @FXML
    private Label worldDisplayTime;
    @FXML
    private FlowPane BuildingItemList;
    @FXML
    private Tab BuildingsTab;
    @FXML
    private Button decreaseBuildersButton;
    @FXML
    private Tab TradeGoodsTab;
    @FXML
    private Button decreaseWorkersButton1;
    @FXML
    private Label TradeGoodTabErrorText;
    @FXML
    private Tab FarmersTab;
    @FXML
    private Label FarmerTabErrorText;
    @FXML
    private Tab WarriorTab;
    @FXML
    private FlowPane WarriorFlowPane;
    @FXML
    private Label WarriorsTabErrorText;
    @FXML
    private Label displayMapInfo;
    @FXML
    private Label TradeGoodsTabErrorText;
    @FXML
    private Label BuildingsTabErrorText;
    @FXML
    private FlowPane CropsFlowPanel;
    @FXML
    private Label FarmingTabFoodAvailable;
    @FXML
    private Label FarmingTabFoodConsumptionRate;
    @FXML
    private Label worldDisplayFoodAvailable;
    @FXML
    private Label SocialCohesionPercentage;
    @FXML
    private Canvas CanvasMapDisplayTerritoryLayer;
    @FXML
    private VBox ProductionQueueList;
    @FXML
    private VBox WarriorTrainingQueueList;
    @FXML
    private Tab RaidingTab;
    @FXML
    private Tab TradeTab;
    
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
        
        BuildingsTabErrorText.setText("");
        TradeGoodsTabErrorText.setText("");
        WarriorsTabErrorText.setText("");
        FarmerTabErrorText.setText("");
        displayMapInfo.setText("");
        //myAutomapper.setScreenXYSize((int)CanvasMapDisplay.getWidth(),(int)CanvasMapDisplay.getHeight());
        myMain = aThis;
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        this.assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        updatePossibleToMake();
        
        
    }

    
    public void updatePossibleToMake()
    {
        
       updateAvailableItems();
       updateAvailableBuildings();
       updateAvailableWarriors();
    }
    
    
    
    
   
    public void setTime(String aTime) {
        worldDisplayTime.setText(aTime);
    }
    
    public void updateAvailableWarriors()
    {
        WarriorFlowPane.getChildren().clear();
        Iterator<WarriorTrainingDisplayData> aWarriorIterator =  myMain.getPlayersCamp().getWarriorHandler().getWarriorsAvailable();
        while(aWarriorIterator.hasNext())
        {
            WarriorTrainingDisplayData myData = aWarriorIterator.next();
            QuickTipsWarriorTraining aButton  = new QuickTipsWarriorTraining(myData, new Tooltip(myData.getDescription() + "\nStrength" + myData.getStrength() +"\n\n" + myData.getTotalBuildTime()));

            
            aButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                Button myButton = aButton;
                @Override
                public void handle(MouseEvent event) {
                   WarriorTrainingDisplayData ret = myMain.addWarrior(aButton.getText());
                   if(ret != null)
                   {
                       WarriorTrainingQueueList.getChildren().add(new CustomHBox(ret, myMain));
                   }
                }           
            });
            WarriorFlowPane.getChildren().add(aButton);
        }
    }
    
    public void updateAvailableItems()
    {
        TradeFlowpane.getChildren().clear();
        Iterator<ProductProductionDisplayData> aProductIterator =  myMain.getPlayersCamp().getProductionHandler().getTradeGoodsAvailable();
        
        while(aProductIterator.hasNext())
        {
            
            ProductProductionDisplayData myData = aProductIterator.next();
            QuickTipsProductionButton aButton = new QuickTipsProductionButton(myData, new Tooltip(myData.getDescription() + "\n\n" + myData.getTotalBuildTime()));
            aButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                Button myButton = aButton;
                
                @Override
                public void handle(MouseEvent event) {
                    ProductProductionDisplayData myData = myMain.setProductionFocus(aButton.getData());
                    if(myData != null)
                    {
                        ProductionQueueList.getChildren().add(new CustomHBox(myData, myMain));
                    }
                }           
            });
            TradeFlowpane.getChildren().add(aButton);
        }
    }
    
    public void updateAvailableBuildings()
    {
        BuildingItemList.getChildren().clear();
        Iterator<BuildingConstructionDisplayData> anIterator = myMain.getPlayersCamp().getBuildingHandler().getBuildable().iterator();
        while(anIterator.hasNext())
        {
            BuildingConstructionDisplayData myData = anIterator.next();
            Button aButton = new QuickTipsBuildButton(myData, new Tooltip(myData.getDescription() + "\n\n" + myData.getTotalBuildTime())); //For now use button later override it
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
    
    
    
    public void updateQueues()
    {
        if(myMain.hasToUpdateWarriors())
        {
            updateAvailableWarriors();
        }
        
        if(myMain.hasToUpdateBuildings())
        {
            updateAvailableBuildings();
        }
        
        if(myMain.hasToUpdateItems())
        {
            updateAvailableItems();
        }
        
       Iterator<Node> aNode = BuildingQueueList.getChildren().iterator();
       while(aNode.hasNext())
       {
           Node A = aNode.next();
           ((CustomHBox)A).update();
            if(((CustomHBox)A).isDone())
            {
                aNode.remove();
                AutoMapperGui.redrawMap();
            }
       }
       
       aNode = ProductionQueueList.getChildren().iterator();
       while(aNode.hasNext())
       {
           Node A = aNode.next();
           ((CustomHBox)A).update();
            if(((CustomHBox)A).isDone())
            {
                aNode.remove();
            }
       }
       
       aNode = WarriorTrainingQueueList.getChildren().iterator();
       while(aNode.hasNext())
       {
           Node A = aNode.next();
           ((CustomHBox)A).update();
            if(((CustomHBox)A).isDone())
            {
                aNode.remove();
                AutoMapperGui.redrawMap();
            }
       }
    }
    
    public void setFoodAvailable(int amount)
    {
        worldDisplayFoodAvailable.setText("" + amount);
        //.setText("" + amount);
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
                    BuildingQueueList.getChildren().add(new CustomHBox(myConstruction, this.myMain));
                }
           }
        }
    }

    private void MouseDraggingDetectedOnCanvas(MouseEvent event) {
        int i = 0;
    }

    public void setSocialCohesion(int Amount)
    {
        SocialCohesionPercentage.setText(""+Amount);
    }
    
    
    
    public void updateMap() {
        myAutomapper.setScreenXYSize((int)CanvasMapDisplay.getWidth(), (int)CanvasMapDisplay.getHeight());
        myAutomapper.setCanvasBackgroundLayer(CanvasMapDisplay.getGraphicsContext2D(), CanvasMapDisplay.getWidth(), CanvasMapDisplay.getHeight());
        myAutomapper.setCanvasBackgroundLayer2(CanvasMapDisplayTerritoryLayer.getGraphicsContext2D(), CanvasMapDisplayTerritoryLayer.getWidth(), CanvasMapDisplayTerritoryLayer.getHeight());
    }

    public void redrawMap()
    {
        AutoMapperGui.redrawMap();
        //myAutomapper.setCanvas(CanvasMapDisplay.getGraphicsContext2D(), CanvasMapDisplay.getWidth(), CanvasMapDisplay.getHeight());
        myAutomapper.Draw();
    }
    
    @FXML
    private void increaseWorkersButtonClick(ActionEvent event) {
        myMain.increaseWorkers();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
    }

    @FXML
    private void decreaseWorkersButtonClick(ActionEvent event) {
        myMain.decreaseWorkers();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
    }

    @FXML
    private void increaseBuildersButtonClick(ActionEvent event) {
        
        myMain.increaseBuilders();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
    }

    @FXML
    private void increaseFarmersButtonClick(ActionEvent event) {
        myMain.increaseFarmers();
    
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        this.assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
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

    
    boolean Dragging = false;
    double MouseScreenX;
    double MouseScreenY;
    double startMouseScreenX;
    double startMouseScreenY;
   

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
        if(BuildingSelectedForBuilding == null)
        {
            displayMapInfo.setTextFill(Paint.valueOf("white"));
            MapTile aTile = this.myAutomapper.getTileAtMouseCoordinates(event.getSceneX() - CanvasMapDisplay.getLayoutX(), event.getSceneY() - CanvasMapDisplay.getLayoutY());
            if(aTile != null)
            {
                displayMapInfo.setText(aTile.toString());
            } else {
                displayMapInfo.setText("");
            }
        }
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

   
    @FXML
    private void decreaseBuildersButtonClick(ActionEvent event) {
        this.myMain.decreaseBuilders();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
    }

    @FXML
    private void decreaseFarmersButtonClick(ActionEvent event) {
        this.myMain.decreaseFarmers();
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
    }

    public void setMapMessage(String aMessage) {
        if(aMessage != null)
        {
            displayMapInfo.setText(aMessage);
        } else {
             displayMapInfo.setText("");
        }
    }

    
    
    public void showErrorMessage(String ErrorString) {
        if(CanvasMapDisplay.isFocused())
        {
            displayMapInfo.setTextFill(Paint.valueOf("red"));
            setMapMessage(ErrorString);
        } 
        else if (WarriorTab.isSelected())
        {
            WarriorsTabErrorText.setTextFill(Paint.valueOf("red"));
            WarriorsTabErrorText.setText(ErrorString);
        } 
        else if (FarmersTab.isSelected())
        {
            FarmerTabErrorText.setTextFill(Paint.valueOf("red"));
            FarmerTabErrorText.setText(ErrorString);
        }
        else if (TradeGoodsTab.isSelected())
        {
            TradeGoodsTabErrorText.setTextFill(Paint.valueOf("red"));
            TradeGoodsTabErrorText.setText(ErrorString);
        }
        else if (BuildingsTab.isSelected())
        {
            BuildingsTabErrorText.setTextFill(Paint.valueOf("red"));
            BuildingsTabErrorText.setText(ErrorString);
        }
    }
    
    

    boolean FocusInBuildingsTab = false;
    @FXML
    private void BuildingsTabSelected(Event event) {
        

    }
    
    boolean FocusInTradeGoodTab = false;
    @FXML
    private void TradeGoodTabSelected(Event event) {

    }
    
    boolean FocusInFarmersTab = false;
    @FXML
    private void FarmersTabSelected(Event event) {
        
  
    }

    boolean FocusInWarriorsTab = false;
    @FXML
    private void WarriorTabSelected(Event event) {

    }

    boolean CanvasCtrlHeld = false;
    
    @FXML
    private void CanvasKeyPressed(KeyEvent event) {
         CanvasCtrlHeld = event.isControlDown();
            
    }

    @FXML
    private void CanvasMapMouseExited(MouseEvent event) {
    }

    @FXML
    private void CanvasMapMouseEntered(MouseEvent event) {
        

    }

    @FXML
    private void CanvasKeyReleased(KeyEvent event) {
         CanvasCtrlHeld = event.isControlDown();
    }

    public void setFoodConsumptionTab(float foodConsumptionPerDay) {
        FarmingTabFoodConsumptionRate.setText("" + (int)Math.floor(foodConsumptionPerDay));
    }

    public void setFoodProductionTab(float foodProducedPerDay) {
        FarmingTabFoodAvailable.setText("" + (int)Math.floor(foodProducedPerDay));
    }

    public void updateAllCitizens() {
        worldDisplayFreeCitizensCount.setText("" + myMain.getPlayersCamp().getFreeCitizens());
        assignCitizensFarmersCount.setText("" + myMain.getPlayersCamp().getFoodHandler().getFarmersAmount());
        this.assignCitizensWorkersCount1.setText("" + myMain.getPlayersCamp().getProductionHandler().getProducersAmount());
        assignCitizensBuildersCount.setText("" + myMain.getPlayersCamp().getBuildingHandler().getBuildersAmount());
        this.updateAvailableWarriors();
    }

    

    
}
