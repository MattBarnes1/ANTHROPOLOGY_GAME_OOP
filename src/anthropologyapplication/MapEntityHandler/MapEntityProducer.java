/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.MapEntityHandler;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.MapEntities.MerchantEntityObject;
import anthropologyapplication.MapEntities.RaidEntityObject;
import anthropologyapplication.TradeGoods.TradeGood;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import java.util.ArrayList;
import java.util.Iterator;
import anthropologyapplication.Logger.FileLogger;
/**
 *
 * @author noone
 */
public class MapEntityProducer {
    public static ArrayList<MapEntityObject> myEntities = new ArrayList<>();
    public static void update(GameTime MS)
    {
        Iterator<MapEntityObject> myEntityIterator = myEntities.iterator();
        while(myEntityIterator.hasNext())
        {
            MapEntityObject myObject = myEntityIterator.next();
            myObject.update(MS);
            if(myObject.shouldDelete())
                myEntityIterator.remove();
        }
    }
    
    public static void createWarriorEntity(TribalCampObject OriginTribe, MapTile Origin, MapTile Destination, Warrior[] WarriorsToSend)
    {
        String myEntityImage = decideImageBasedOnWarriors(WarriorsToSend);
        RaidEntityObject myObject = new RaidEntityObject(OriginTribe, Destination, Origin, WarriorsToSend, myEntityImage);
        FileLogger.writeToLog(FileLogger.LOGTO.MAP_ENTITIES, myObject.getClass().toString() + " with Entity ID:" + myObject.hashCode(), "Warrior Entity: spawned on the Map!");
        myEntities.add(myObject);
    }
    
    
    
    public static void createMerchantEntity(TribalCampObject OriginTribe, MapTile Origin, MapTile Destination, TradeGood[] tradeGoodsToSend)
    {
        MerchantEntityObject myObject = new MerchantEntityObject(OriginTribe, Destination, Origin, "Merchant.jpg", tradeGoodsToSend);
        FileLogger.writeToLog(FileLogger.LOGTO.MAP_ENTITIES, myObject.getClass().toString() + " with Entity ID:" + myObject.hashCode(), "Merchant Entity: spawned on the Map!");
        myEntities.add(myObject);
    }

    private static String decideImageBasedOnWarriors(Warrior[] WarriorsToSend) {
        return "Warriors.jpg";
    }
    
    static void removeMapEntity(MapEntityObject anObjectToRemove) // internal to the class
    {
        FileLogger.writeToLog(FileLogger.LOGTO.MAP_ENTITIES, anObjectToRemove.getClass().toString() + " with Entity ID:" + anObjectToRemove.hashCode(), "Map Entity: Removing from Map!");
        myEntities.remove(anObjectToRemove);
    }
    
}
