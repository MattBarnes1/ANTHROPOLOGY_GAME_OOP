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
        myMap = new Map(10);
        MapThread = new Thread(myMap);
        MapThread.start();
        myRandomEvents = new RandomEventHandler("/RandomEvents.json");
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
        myDisplay.displayMainGameScreen(this, myMap);
        myTimer.start();
    }
    
    
    GameTime WorldTime;
    private float updateInterval = 20000000;
    
    public MainGameCode(GuiHandler mainHandler) {
        myTimer = new AnimationTimer()
        {
            Double updateInternalTime = 0D;
            public void handle(long currentNanoTime)
            {
                
                updateInternalTime += currentNanoTime;
                if(updateInternalTime >= updateInterval)
                {
                    WorldTime.Update(10);
                    for(AICampObject anObject : myEnemyArray)
                    {
                        anObject.update(WorldTime);
                    }
                    mainHandler.updateTime(WorldTime.getTimeString12Hour());
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
        
    }
    
    public void decelerateTime()
    {
        
    }

    public void createSocietyValues(ArrayList<SocialValues> myValues) {
        myPlayerSocietyChoices = new SocietyChoices(myValues);
        playersCamp = new TribalCampObject(myPlayerSocietyChoices);
        while(MapThread.isAlive()){}; //wait until map thread terminates
        playersCamp.setHomeTile(myMap.getPlayerMapTile());
        myDisplay.displayMainGameScreen(this, myMap);
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
        myDisplay.displayMainGameScreen(this, myMap);
    }

    public TribalCampObject getPlayersCamp() {
        return playersCamp;
    }
}
