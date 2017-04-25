/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.WorldEntityHandler;

import anthropologyapplication.AutoMapper.MapTile;
import anthropologyapplication.GameTime;
import anthropologyapplication.MapEntities.MerchantEntityObject;
import anthropologyapplication.MapEntities.RaidEntityObject;
import anthropologyapplication.TradeGoods.TradeGood;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Warrior;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author noone
 */
public class WorldMapEntityProducer {
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
        myEntities.add(new RaidEntityObject(OriginTribe, Destination, Origin, WarriorsToSend, myEntityImage));
    }
    
    public static void createMerchantEntity(TribalCampObject OriginTribe, MapTile Origin, MapTile Destination, TradeGood[] tradeGoodsToSend)
    {
        myEntities.add(new MerchantEntityObject(OriginTribe, Destination, Origin, "Merchant.jpg", tradeGoodsToSend));
    }

    private static String decideImageBasedOnWarriors(Warrior[] WarriorsToSend) {
        return "Warriors.jpg";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
