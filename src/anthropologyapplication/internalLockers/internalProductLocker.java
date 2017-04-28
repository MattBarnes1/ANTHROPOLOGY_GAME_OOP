/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.internalLockers;

import anthropologyapplication.TradeGoods.TradeGood;

/**
 *
 * @author Duke
 */
public class internalProductLocker {
      public boolean Available = true;
    public TradeGood myTradeGood;
    public internalProductLocker(TradeGood aTradeGood, boolean isAvailable)
    {
        this.Available = isAvailable;
        myTradeGood = aTradeGood;
    }

    public TradeGood getTradeGood()
    {
        return myTradeGood;
    }
    
    public String getName() {
        return myTradeGood.getName();
    }

    public int getAmount() {
        return myTradeGood.getAmount();
    }

    public void reduceAmount(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
