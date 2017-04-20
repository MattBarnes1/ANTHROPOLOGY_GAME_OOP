/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TradeGoods;

import TradeGoods.ProductionHandler;
import anthropologyapplication.GameTime;
import anthropologyapplication.Timer;
import anthropologyapplication.TribalCampObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public abstract class TradeGood
{
        private int TotalAmount = 0;
        private String TradeGoodName;
        private final Timer productionTime;
        private Timer currentProductionTime;
        private int baseSellValue;
        public void incrementAmount()
        {
            TotalAmount++;
        }

        public void setAmount(int Amount)
        {
            TotalAmount = Amount;
        }
        
        public void decrementAmount()
        {
            if((TotalAmount - 1) < 0)
            {
                TotalAmount = 0;
            } else {
                TotalAmount--;
            }
        }

        public TradeGood(String Name, int startingTradeGoodAmount, Timer ProductionTime, int baseSellVal)
        {
                this.baseSellValue = baseSellValue;
                TradeGoodName = Name;
                TotalAmount = startingTradeGoodAmount;
                productionTime = ProductionTime;
                currentProductionTime = ProductionTime;
        }

        
        
    public String getName() {
        return TradeGoodName;
    }

    int amount = 0;
    
    public int getAmount() {
        return amount;
    }
    
    
    
    public abstract void update(GameTime MS, TribalCampObject myObject);

    public String getTotalTimeToBuild() {
        return this.productionTime.toString();
    }

    public String getCurrentBuildTime() {
        return this.currentProductionTime.toString();
    }

    public double getCompletionPercentage() {
       return currentProductionTime.dividedBy(productionTime);
    }
}
