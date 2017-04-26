/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import anthropologyapplication.RandomEvents.RandomEventHandler;
import anthropologyapplication.AIStuff.AICampObject;
import anthropologyapplication.Buildings.Building;
import anthropologyapplication.MapEntityHandler.MapEntityProducer;
import anthropologyapplication.DisplayData.BuildingConstructionDisplayData;
import anthropologyapplication.DisplayData.ProductProductionDisplayData;
import anthropologyapplication.DisplayData.WarriorTrainingDisplayData;
import anthropologyapplication.DisplayData.DisplayData;
import anthropologyapplication.TradeGoods.TradeGood;
import anthropologyapplication.Warriors.Warrior;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Worker.State;
import javax.naming.OperationNotSupportedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class MainGameCode {

    GuiHandler myDisplay;
    TribalCampObject playersCamp;
    AICampObject[] myEnemyArray;
    SocietyChoices myPlayerSocietyChoices;
    RandomEventHandler myRandomEvents;
    boolean isPaused = false;
    AnimationTimer myTimer;
    Map myMap;
    Thread MapThread;
    Thread RandomEventsThread;
    public void newGame() throws IOException, OperationNotSupportedException {
        myMap = new Map(50, this, myDisplay.getAutomapper());
        myDisplay.setupMap();
        //myMap.run();
        
        myMap.start();
       // myRandomEvents = new RandomEventHandler("anthropologyApplication/RandomEvents/RandomEvents.json");
       //TODO: myRandomEvents.start();
        
        
        myDisplay.displaySocietyChoiceSelectionGUI(this);
    }

    public void saveGame() {
        playersCamp.saveAntagonists(myEnemyArray);
        playersCamp.saveMap(myMap);
        myDisplay.displaySaveGameGUI(this, playersCamp);
    }

    public void loadPlayerCamp(TribalCampObject loadedPlayersCamp)
    {
        playersCamp = loadedPlayersCamp;
        myEnemyArray = playersCamp.loadAntagonists();
        myMap = playersCamp.loadMap();
        myDisplay.displayMainGameScreen(this);
        myTimer.start();
    }
    
    
    GameTime WorldTime = new GameTime();
    private float updateInterval = 20000000;
    private boolean increaseSpeed = false;
    public MainGameCode(GuiHandler mainHandler) {
        myTimer = new AnimationTimer()
        {
            Double updateInternalTime = 0D;
            public void handle(long currentNanoTime)
            {
                updateInternalTime += currentNanoTime;
                if(updateInternalTime >= updateInterval)
                {
                    //float startTime =  System.nanoTime();
                    if(increaseSpeed)
                    {
                        WorldTime.Update(6000000);
                    } else {
                        WorldTime.Update(10);
                    }
                    MapEntityProducer.update(WorldTime);
                    for(AICampObject anObject : myEnemyArray)
                    {
                        anObject.update(WorldTime);
                    }
                    playersCamp.update(WorldTime);
                    //int amount = (int)Math.floor(((float)playersCamp.getFoodHandler().getTotalFood() -  playersCamp.getPopulationHandler().getFoodConsumptionPerDayInPerMilliSecond()*10));
                    mainHandler.setFarmingTabData(playersCamp.getFoodHandler().getFoodProducedPerDay(),  playersCamp.getPopulationHandler().getFoodConsumptionPerDay());
                    mainHandler.updateFood(playersCamp.getFoodHandler().getTotalFood());
                    mainHandler.updateBuildQueue();
                    mainHandler.updateTime(WorldTime.getTimeString12Hour());
                    mainHandler.drawMainGameScreenMap();
                    //myMapper.Draw(Automapper.getGraphicsContext2D());

                    updateInternalTime = 0D;
                   // float endTime =  System.nanoTime();
                   // System.out.println("Update Loop Time: " + (endTime-startTime));
                    
                }
            }
        };
        this.myDisplay = mainHandler;
        mainHandler.displayMainMenuGUI(this);
    }

    public void loadGame() {
        myDisplay.displayLoadGameDisplay(this);
    }

    public void accelerateTime()
    {
        increaseSpeed = true;
    }
    
    public void decelerateTime()
    {
        increaseSpeed = false;
    }

    public void createSocietyValues(ArrayList<SocialValues> myValues) throws Throwable {
        myPlayerSocietyChoices = new SocietyChoices(myValues);
        playersCamp = new TribalCampObject(myPlayerSocietyChoices);
        if(myMap.stateProperty().get() ==  State.SCHEDULED || myMap.stateProperty().get() ==  State.RUNNING)
        {
            this.myDisplay.displayCreatingWorldScreen(this);
        }
       else if (myMap.stateProperty().get() ==  State.FAILED)
       {
              if(myMap.getException() != null)
              {
                throw myMap.getException();
              } else {
                  throw new Exception("Unknown Map Builder Error");
              }
       }
        else {
            System.out.println(myMap.stateProperty().get());
            if(playersCamp.getMapTileLocation() == null)
            {
                finishMapSetup();
            }
        }
    }

    public void resetMap()
    {
        myMap.getTerrainGenerator().start();
        myMap.start();
    }
    
    
    public void finishMapSetup()
    {
        myDisplay.getAutomapper().setRoomFocus(myMap.getPlayerMapTile().getCoordinates());
        myEnemyArray = myMap.getCamps();
        myDisplay.displayMainGameScreen(this);
        playersCamp.setHomeTile(myMap.getPlayerMapTile());
        for(AICampObject A : myEnemyArray)
        {
            A.startAI();
        }
        myTimer.start();
    }
    
    public void checkEventVsPlayerValues() {
        throw new NotImplementedException();
    }

    public void pauseGame() {
        myTimer.stop();
        myDisplay.displayPauseMenuGui(this);
    }
    
    public void unpauseGame() {
        myTimer.start();
        myDisplay.displayMainGameScreen(this);
    }

    public TribalCampObject getPlayersCamp() {
        return playersCamp;
    }

    public void increaseWorkers() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getProductionHandler().addProducers(1);
        } else {
            myDisplay.setErrorMessage("You don't have enough people!"); //Error messages are red
        }
    }

    public void decreaseWorkers() {        
        if(playersCamp.getProductionHandler().getProducersAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getProductionHandler().removeProducers(1);
        } else {
            myDisplay.setErrorMessage("You have no farmers to remove!"); //Error messages are red
        }
    }

    public void increaseWarriors() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getWarriorHandler().addWarriorToTraining("Warrior");
        } else {
            myDisplay.setErrorMessage("You have no farmers to remove!"); //Error messages are red
        }
    }

    public void decreaseWarriors() {
        if(playersCamp.getWarriorHandler().getWarriorsAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getWarriorHandler().removeWarriorFromTraining("Warrior");
        } else {
            myDisplay.setErrorMessage("You have no farmers to remove!"); //Error messages are red
        }
    }

    public void increaseBuilders() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getBuildingHandler().addBuilders(1);
        } else {
            myDisplay.setErrorMessage("You don't have enough people!"); //Error messages are red
        }
    }
    
    public void decreaseBuilders() {
        if(playersCamp.getBuildingHandler().getBuildersAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getBuildingHandler().removeBuilders(1);
        } else {
            myDisplay.setErrorMessage("You have no builders to remove!"); //Error messages are red
        }
    }
    public void increaseFarmers() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getFoodHandler().addFarmers(1);
        } else {
            myDisplay.setErrorMessage("You don't have enough people!"); //Error messages are red
        }
    } 
    public void decreaseFarmers() {
        if(playersCamp.getFoodHandler().getFarmersAmount() > 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getFoodHandler().removeFarmers(1);
        } else {
            myDisplay.setErrorMessage("You have no farmers to remove!"); //Error messages are red
        }
    }

    public String getMessage() throws Throwable
    {
       if(myMap.stateProperty().get() ==  State.SCHEDULED || myMap.stateProperty().get() ==  State.RUNNING)
       {
            return myMap.getMessage();
       } 
       else if (myMap.stateProperty().get() ==  State.FAILED)
       {
              if(myMap.getException() != null)
              {
                throw myMap.getException();
              } else {
                  throw new Exception("Unknown Map Builder Error");
              }
       } else {
           return "Error, this should never display";
       }
    }
    
    
    public double getProgress() throws Throwable {
           if(myMap.stateProperty().get() ==  State.SCHEDULED || myMap.stateProperty().get() ==  State.RUNNING)
           {
                return myMap.getProgress();
           } 
           else if (myMap.stateProperty().get() ==  State.FAILED)
           {
              if(myMap.getException() != null)
              {
                throw myMap.getException();
              } else {
                  throw new Exception("Unknown Map Builder Error");
              }
           } else {
                return -1;
           }
       }

    public void addWarrior(String text) {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.getWarriorHandler().addWarriorToTraining(text);
            if(playersCamp.getWarriorHandler().hasError())
            {
               this.myDisplay.setErrorMessage(playersCamp.getWarriorHandler().getError());
            } else {
                playersCamp.removeFreeCitizen();
            }
            
        } else {
            this.myDisplay.setErrorMessage("Not enough citizens to build this!");
            
        }
    }

    public void setProductionFocus(String text) {
        if(playersCamp.getProductionHandler().canAddTradeGood())
        {
            playersCamp.getProductionHandler().addProductionItem(text);
        } else {
            this.myDisplay.setErrorMessage("You're not able to add a new item because there's not enough producers!");
        }
    }

    public void continueGamePressed() {
        this.myDisplay.displayMainGameScreen(this);
        this.unpauseGame();
    }

    public void loadGameHasBeenPressed() {
        myDisplay.displayLoadGameDisplay(this);
    }

    public void startLoadingFromFile(File theFileToLoadFrom)
    {
        
    }
    
    public void SaveGameHasBeenPressed() {
        this.saveGame();
        myDisplay.displaySaveGameGUI(this, playersCamp); //so they can name it and save it
    }

    public void returningToMainMenu() {
        myEnemyArray = null;
        playersCamp = null;
        myDisplay.displayMainMenuGUI(this);
    }


    public void remove(Building ProgressData) {
        playersCamp.getBuildingHandler().stopBuilding(ProgressData);
    }

    public void remove(TradeGood ProgressData)
    {
        playersCamp.getProductionHandler().removeProductFromProduction(ProgressData);
    }
    
    public void remove(Warrior ProgressData)
    {
        playersCamp.getWarriorHandler().removeWarriorFromTraining(ProgressData);
    }

    
}
