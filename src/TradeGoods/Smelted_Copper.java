/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGoods;

import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import anthropologyapplication.Warriors.Spearmen;

/**
 *
 * @author noone
 */
class Smelted_Copper extends TradeGood {

    public Smelted_Copper(String Name, int startingTradeGoodAmount, Timer ProductionTime, int baseSellVal) {
        super(Name, startingTradeGoodAmount,ProductionTime, baseSellVal);
    }

    @Override
    public void update(GameTime MS, TribalCampObject myObject) {
       
    }
    
}
