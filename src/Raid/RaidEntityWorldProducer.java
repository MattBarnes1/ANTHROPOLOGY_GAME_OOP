/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Raid;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author noone
 */
public class RaidEntityWorldProducer {
    public static ArrayList<RaidEntityObject> myEntities = new ArrayList<>();
    public static void update(GameTime MS)
    {
        Iterator<RaidEntityObject> myEntityIterator = myEntities.iterator();
        while(myEntityIterator.hasNext())
        {
            RaidEntityObject myObject = myEntityIterator.next();
            myObject.update(MS);
        }
    }
    
    public static void createEntity(TribalCampObject OriginTribe, MapTile Origin, MapTile Destination, Warrior[] WarriorsToSend)
    {
        myEntities.add(new RaidEntityObject(OriginTribe, Origin, Destination, WarriorsToSend));
    }
    
    
    
}
