/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication.DisplayData;

import anthropologyapplication.TradeGoods.TradeGood;

/**
 *
 * @author Duke
 */
public class ProductProductionDisplayData {
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
    
}
