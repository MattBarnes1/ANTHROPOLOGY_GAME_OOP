/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.TradeGoods;

import anthropologyapplication.Buildings.Blacksmith;
import anthropologyapplication.Buildings.Homes;
import anthropologyapplication.Buildings.Smelterer;
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
         super.updateTimer(MS);
         
    }


    
}
