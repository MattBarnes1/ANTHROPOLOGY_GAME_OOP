/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapEntities;

import anthropologyapplication.WorldEntityHandler.MapEntityObject;
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
public class RaidEntityObject extends MapEntityObject {
    Random myRandom = new Random();
    Warrior[] myWarriors;
    public RaidEntityObject(TribalCampObject Owner, MapTile Destination, MapTile StartingPoint, Warrior[] myWarriors, String ImageName)
    {
        super(Owner, Destination, StartingPoint, ImageName);
        this.myWarriors = myWarriors;  
    }
  
    
    public Warrior[] getWarriors()
    {
        return this.myWarriors;
    }
    
    
    
    private void Raid(MapTile aTile)
    {
        deleteMe = true;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.  
    }

    private boolean deleteMe = false;
    
    @Override
    public boolean shouldActivate() {
      return true;
    }

    @Override
    public void onArrival(MapTile myDestination) {
        Raid(myDestination);
    }

    @Override
    public boolean shouldDelete() {
        return deleteMe;
    }
}
