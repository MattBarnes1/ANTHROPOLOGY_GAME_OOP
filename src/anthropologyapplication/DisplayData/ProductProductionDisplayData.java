/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.MainGameCode;
import anthropologyapplication.TradeGoods.TradeGood;

/**
 *
 * @author Duke
 */
public class ProductProductionDisplayData implements DisplayData {
    TradeGood myTradeGood;
    public ProductProductionDisplayData(TradeGood myTradeGood) {
        this.myTradeGood = myTradeGood;
    }

    public String getName() {
       return myTradeGood.getName();
    }

    public String getDescription() {
        return ""; //TODO:
    }

    public String getTotalBuildTime() {
        return myTradeGood.getTotalTimeToBuild();
    }
    
    public String getCurrentBuildTime() {
        return myTradeGood.getCurrentBuildTime();
    }
    
    public double getCompletionPercentage()
    {
        return myTradeGood.getCompletionPercentage();
    }
    
        @Override
    public void acceptRemoverAsVisitor(MainGameCode myGameCode) {
        myGameCode.remove(myTradeGood);
    }

    @Override
    public String getTimeToCompleteString() {
        return myTradeGood.getCurrentBuildTime().toString();
    }

    @Override
    public boolean shouldBeRemoved() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getToolTipString() {
       return "" + myTradeGood.getCurrentBuildTime();
    }
    
    
}
