/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raid;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.Path;
import anthropologyapplication.Warriors.Warrior;
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
    public RaidEntityObject(MapTile Destination, MapTile StartingPoint, Warrior[] myWarriors)
    {
        currentMapTile = StartingPoint;
        myPathFinding = new Path(StartingPoint, Destination);        
        myRaider = new RaidEntityAI(this); 
    }
    
    public void update(GameTime MS)
    {
        
    }
    
}
