/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.WorldEntityHandler;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Path;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import java.util.Iterator;

/**
 *
 * @author noone
 */
public abstract class MapEntityObject {
    
    private final Timer updateTime;
    private Timer countdownTimer;
    private TribalCampObject myOwner;
    private MapTile currentMapTile;
    private MapTile onUpdateMapTile; 
    private Path myPathFinding;//Will be created in AI Thread
    int currentPathIndex = 0;
    protected Image myImageOnMap;
    Iterator<MapTile> myTiles = myPathFinding.getInternalPath();
    public MapEntityObject (TribalCampObject Owner, MapTile Destination,MapTile StartingPoint, String ImageName)
    {   
        myImageOnMap = new Image("anthropologyapplication/MapEntities/" + ImageName);
        updateTime = new Timer(1,0,0,0);
        countdownTimer = new Timer(1,0,0,0);//TODO: for now
        currentMapTile = StartingPoint;
        myPathFinding = new Path(StartingPoint, Destination);  
        this.myOwner = Owner;
        myTiles = myPathFinding.getInternalPath();
    }
    
    
    public void update(GameTime MS)
    {
        if(shouldActivate())
        {
            countdownTimer = countdownTimer.subtract(MS.getElapsedTime());
            if(countdownTimer.EqualTo(new Timer(0,0,0,0)))
            {
                if(myTiles.hasNext())
                {
                    MapTile hiddenTile = myTiles.next();
                    currentMapTile = hiddenTile;
                } else {
                    onArrival(currentMapTile);
                }
            }
        }
    }
    public abstract boolean shouldActivate();
    public abstract void onArrival(MapTile myDestination);
    public abstract boolean shouldDelete();
}
