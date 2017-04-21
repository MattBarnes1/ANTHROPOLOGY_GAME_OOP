/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.Raid;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Path;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import java.util.Iterator;
import java.util.Random;
import javafx.scene.image.Image;

/**
 *
 * @author noone
 */
public class RaidEntityObject {
    Random myRandom = new Random();
    private MapTile currentMapTile;
    private MapTile onUpdateMapTile; 
    private Path myPathFinding;//Will be created in AI Thread
    int currentPathIndex = 0;
    private Image myImageOnMap;
    Iterator<MapTile> myTiles = myPathFinding.getInternalPath();
    Warrior[] myWarriors;
    private final Timer updateTime;
    private Timer countdownTimer;
    private TribalCampObject myOwner;
    public RaidEntityObject(TribalCampObject Owner, MapTile Destination, MapTile StartingPoint, Warrior[] myWarriors)
    {
        decideImageBasedOnWarrriors(myWarriors);
        this.myOwner = Owner;
        updateTime = new Timer(1,0,0,0);
        countdownTimer = new Timer(1,0,0,0);
        this.myWarriors = myWarriors;
        currentMapTile = StartingPoint;
        myPathFinding = new Path(StartingPoint, Destination);   
    }
    boolean startedRaid = false;
    public void startRaid()
    {
        startedRaid = true;
    }
    
    public void update(GameTime MS)
    {
        if(startedRaid)
        {
            countdownTimer = countdownTimer.subtract(MS.getElapsedTime());
            if(countdownTimer.EqualTo(new Timer(0,0,0,0)))
            {
                MapTile hiddenTile = myTiles.next();
                if(hiddenTile == null)
                {
                    Raid(currentMapTile);
                } else {
                    currentMapTile = hiddenTile;
                }
            }
        }
    }
    
    public Warrior[] getWarriors()
    {
        return this.myWarriors;
    }
    
    
    
    private void Raid(MapTile aTile)
    {
        
    }

    String Lookup = "anthropologyapplication/Warriors/";
    
    private void decideImageBasedOnWarrriors(Warrior[] myWarriors) {
        for(int i = 0; i < myWarriors.length; i++)
        {
            
        }
        
        
        myImageOnMap = new Image("anthropologyapplication/Warriors/Clubmen.jpg");
    }
}
