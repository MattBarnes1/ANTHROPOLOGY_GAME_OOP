/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Duke
 */
public class TradeGood
{
        int TotalAmount = 0;
        String TradeGoodName;
        Time productionTime;
        int baseSellValue;
        ProductionHandler myHandler;
        String[] GoodsNeededForProduction;
        int[] amountPerGood;
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

        public TradeGood(String Name, int startingTradeGood, Time ProductionTime, int baseSellVal)
        {
                this.baseSellValue = baseSellValue;
                TradeGoodName = Name;
                TotalAmount = startingTradeGood;
                productionTime = ProductionTime;
        }


        public TradeGood(String Name, int Tier, int startingTradeGood, ProductionHandler aHandler, Time ProductionTime, int[] AmountPerGood, String[] GoodsNeedForProduction, int baseSellVal)
        {
            this.amountPerGood = AmountPerGood;
            myHandler = aHandler;
            this.baseSellValue = baseSellValue;
            TradeGoodName = Name;
            TotalAmount = startingTradeGood;
            productionTime = ProductionTime;
        }

    String getName() {
        return TradeGoodName;
    }
}
