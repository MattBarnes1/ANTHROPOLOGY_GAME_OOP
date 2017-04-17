/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raid;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import java.util.Random;

/**
 *
 * @author noone
 */
public class RaidEntityObject {
    Random myRandom = new Random();
    private final MapTile Destination;
    private final MapTile StartingPoint;
    private RaidEntityAI myRaider;
    public RaidEntityObject(MapTile Destination, MapTile StartingPoint)
    {
        this.Destination = Destination;
        this.StartingPoint = StartingPoint;
        myRaider = new RaidEntityAI(this); 
    }
    
    public void update(GameTime MS)
    {
        
    }
    
}
