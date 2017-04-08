/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
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
    public void newGame() throws IOException {
        myMap = new Map(100, this, myDisplay.getAutomapper());
        myDisplay.setupMap();
        //myMap.run();
        myMap.start();
        myRandomEvents = new RandomEventHandler("an/RandomEvents.json");
        RandomEventsThread = new Thread(myRandomEvents);
        
        //RandomEventsThread.start();
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
                    if(increaseSpeed)
                    {
                        WorldTime.Update(60000);
                    } else {
                        WorldTime.Update(10);
                    }
                    for(AICampObject anObject : myEnemyArray)
                    {
                        anObject.update(WorldTime);
                    }
                    mainHandler.updateTime(WorldTime.getTimeString12Hour());
                    mainHandler.drawMainGameScreenMap();
                    playersCamp.update(WorldTime);
                    //myMapper.Draw(Automapper.getGraphicsContext2D());

                    updateInternalTime = 0D;
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

    public void createSocietyValues(ArrayList<SocialValues> myValues) {
        myPlayerSocietyChoices = new SocietyChoices(myValues);
        playersCamp = new TribalCampObject(myPlayerSocietyChoices);
        if(myMap.isRunning())
        {
            this.myDisplay.displayCreatingWorldScreen(this);
        }
        else {
            finishMapSetup();
        }
    }

    
    
    
    public void finishMapSetup()
    {
        playersCamp.setHomeTile(myMap.getPlayerMapTile());
        myDisplay.displayMainGameScreen(this);
        myEnemyArray = myMap.getCamps();
        myDisplay.getAutomapper().setRoomFocus(myMap.getPlayerMapTile().getCoordinates());
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
        }
    }

    public void decreaseWorkers() {        
        if(playersCamp.getProductionHandler().getProducersAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getProductionHandler().removeProducers(1);
        }
    }

    public void increaseWarriors() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getWarriorHandler().addWarriors(1);
        }
    }

    public void decreaseWarriors() {
        if(playersCamp.getWarriorHandler().getWarriorsAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getWarriorHandler().removeWarriors(1);
        }
    }

    public void increaseBuilders() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getBuildingHandler().addBuilders(1);
        }
    }
    
    public void decreaseBuilders() {
        if(playersCamp.getBuildingHandler().getBuildersAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getBuildingHandler().removeBuilders(1);
        }
    }
    public void increaseFarmers() {
        if(playersCamp.hasFreeCitizens())
        {
            playersCamp.removeFreeCitizen();
            playersCamp.getFoodHandler().addFarmers(1);
        }
    } 
    public void decreaseFarmers() {
        if(playersCamp.getFoodHandler().getFarmersAmount() != 0)
        {
            playersCamp.addFreeCitizen();
            playersCamp.getFoodHandler().removeFarmers(1);
        }
    }

    public String getMessage()
    {
       if(myMap.isRunning())
       {
            return myMap.getMessage();
       } else {
           return myDisplay.getAutomapper().getMessage();
       }
    }
    
    public double getProgress() {
       if(myMap.isRunning())
       {
            return myMap.getProgress();
       } else  if(myDisplay.getAutomapper().isRunning()){
           return myDisplay.getAutomapper().getProgress();
       }
           return -1;
       }
}
