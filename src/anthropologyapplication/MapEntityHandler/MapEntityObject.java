/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapEntityHandler;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.PathfindingAI.Path;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import java.util.Iterator;
import javafx.scene.image.Image;

/**
 *
 * @author noone
 */
public abstract class MapEntityObject {
    
    private final Timer resetTime;
    private Timer countdownTimer;
    private TribalCampObject myOwner;
    private MapTile currentMapTile;
    private MapTile onUpdateMapTile; 
    private Path myPathFinding;//Will be created in AI Thread
    int currentPathIndex = 0;
    private Image myImageOnMap;
    Iterator<MapTile> myTiles;
    public MapEntityObject (TribalCampObject Owner, MapTile Destination,MapTile StartingPoint, String ImageName)
    {   
        myImageOnMap = new Image("anthropologyapplication/MapEntities/" + ImageName);
        resetTime = new Timer(1,0,0,0);
        countdownTimer = new Timer(1,0,0,0);//TODO: for now
        currentMapTile = StartingPoint;
        myPathFinding = new Path(StartingPoint, Destination);  
        this.myOwner = Owner;
        myTiles = myPathFinding.getPath();
    }
    
    public void returnToOrigin()
    {
        myTiles = myPathFinding.getReversePath();
    }
    
    
    public TribalCampObject getOwner()
    {
        return myOwner;
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
                    currentMapTile.removeEntityFromTile(this);
                    currentMapTile = myTiles.next();
                    currentMapTile.addEntityToTile(this);
                    countdownTimer = resetTime;
                } else {
                    onArrival(currentMapTile);
                    if(shouldDelete())
                    {
                        currentMapTile.removeEntityFromTile(this);
                        MapEntityProducer.removeMapEntity(this);
                    } else {
                        countdownTimer = resetTime;
                    }
                }
            }
        }
    }
    public abstract boolean shouldActivate();
    public abstract void onArrival(MapTile myDestination);
    public abstract boolean shouldDelete();

    public Image getImage() {
        return myImageOnMap;
    }
}
