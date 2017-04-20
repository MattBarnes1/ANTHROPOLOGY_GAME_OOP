/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raid;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Path;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author noone
 */
public class RaidEntityObject {
    Random myRandom = new Random();
    private RaidEntityAI myRaider;
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
        myImageOnMap = new Image("anthropologyapplication/UnexploredTile.jpeg");
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
}
