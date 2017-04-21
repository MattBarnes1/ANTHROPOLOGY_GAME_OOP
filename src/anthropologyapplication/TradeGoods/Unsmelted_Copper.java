/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TradeGoods;

import anthropologyapplication.Buildings.Smelterer;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
public class Unsmelted_Copper extends TradeGood {

    public Unsmelted_Copper(String Name, int startingTradeGoodAmount, Timer ProductionTime, int baseSellVal) {
        super(Name, startingTradeGoodAmount,ProductionTime, baseSellVal);
    }
    @Override
    public void update(GameTime MS, TribalCampObject myObject) {
        if(this.getAmount() > 10 && myObject.getBuildingHandler().hasWorking(Smelterer.class))
        {
            myObject.getProductionHandler().unlockTradeGood(Unsmelted_Copper.class);
        } else {
            myObject.getProductionHandler().lockTradeGood(Unsmelted_Copper.class);
        }
    }
    
}
