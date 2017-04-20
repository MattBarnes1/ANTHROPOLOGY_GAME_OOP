/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGoods;

import Buildings.Blacksmith;
import Buildings.Homes;
import Buildings.Smelterer;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;

/**
 *
 * @author noone
 */
class Clay extends TradeGood {

    public Clay(String Name, int startingTradeGoodAmount, Timer ProductionTime, int baseSellVal) {
        super(Name, startingTradeGoodAmount,ProductionTime, baseSellVal);
    }

    @Override
    public void update(GameTime MS, TribalCampObject myObject) {
         if(super.getAmount() > 20 && myObject.getProductionHandler().getAmountByString("Wood") > 20)
         {
            myObject.getBuildingHandler().unlockBuilding(Homes.class);
         }
         else {
            myObject.getBuildingHandler().lockBuilding(Homes.class);
         }
         if(super.getAmount() > 50 && myObject.getProductionHandler().getAmountByString("Wood") > 50
                 && myObject.getProductionHandler().getAmountByString("Stone") > 50)
         {
            myObject.getBuildingHandler().unlockBuilding(Smelterer.class);
         } else {
             myObject.getBuildingHandler().lockBuilding(Smelterer.class);
         }
         if(super.getAmount() > 100 && myObject.getProductionHandler().getAmountByString("Wood") > 100
                 && myObject.getProductionHandler().getAmountByString("Stone") > 100)
         {
            myObject.getBuildingHandler().unlockBuilding(Blacksmith.class);
         } else {
             myObject.getBuildingHandler().lockBuilding(Blacksmith.class);
         }
    }


    
}
