/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapEntities;

import anthropologyapplication.MapEntityHandler.MapEntityObject;
import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.PathfindingAI.Path;
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
public class RaidEntityObject extends MapEntityObject {
    Random myRandom = new Random();
    Warrior[] myWarriors;
    MapTile Origin;
    public RaidEntityObject(TribalCampObject Owner, MapTile Destination, MapTile StartingPoint, Warrior[] myWarriors, String ImageName)
    {
        super(Owner, Destination, StartingPoint, ImageName);
        this.myWarriors = myWarriors;  
        Origin = StartingPoint;
    }
  
    
    public Warrior[] getWarriors()
    {
        return this.myWarriors;
    }
       
    
    private void Raid(MapTile aTile)
    {
        if(hasLivingWarriors())
        {
            this.returnToOrigin();
        } else {
            deleteMe = true;
        }
    }

    private boolean deleteMe = false;
    
    @Override
    public boolean shouldActivate() {
      return true;
    }

    @Override
    public void onArrival(MapTile myDestination) {
        if(Origin != myDestination)
        {
            Raid(myDestination);
        } else {
            
            deleteMe = true;
        }
    }

    @Override
    public boolean shouldDelete() {
        return deleteMe;
    }

    private boolean hasLivingWarriors() {
        for(int i = 0; i < this.myWarriors.length; i++)
        {
            if(myWarriors[i] != null)
            {
                return true;
            }
        }
        return false;
    }
}
